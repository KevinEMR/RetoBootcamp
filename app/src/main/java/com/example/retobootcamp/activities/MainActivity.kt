package com.example.retobootcamp.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.retobootcamp.databinding.ActivityMainBinding
import com.example.retobootcamp.services.api.UsuariosApi
import com.example.retobootcamp.services.model.Users
import com.example.retobootcamp.utilities.UIApplication.Companion.prefs
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        checkUserValues()
    }

    private fun checkUserValues(){
        if (prefs.getUsername().isNotEmpty()){
            goToHome()
        }
    }

    private fun initUI(){
        binding.btningresar.setOnClickListener {checkData()}
        val executor: Executor = ContextCompat.getMainExecutor(this@MainActivity)
            val biometricPrompt = BiometricPrompt(this@MainActivity, executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int,
                                                       errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        Toast.makeText(applicationContext,
                            "Authentication error: $errString", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onAuthenticationSucceeded(
                        result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        Toast.makeText(applicationContext,
                            "Authentication succeeded!", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Toast.makeText(applicationContext, "Authentication failed",
                            Toast.LENGTH_SHORT)
                            .show()
                    }
                })

        val promptInfo: BiometricPrompt.PromptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build()

            // Prompt appears when user clicks "Log in".
            // Consider integrating with the keystore to unlock cryptographic operations,
            // if needed by your app.
            binding.btnhuella.setOnClickListener {
                biometricPrompt.authenticate(promptInfo)
        }
    }
    private fun checkData(){
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        lateinit var response : Users
        if(binding.editTextTextEmailAddress.text.isNotEmpty() && binding.editTextTextPassword.text.isNotEmpty()) {
            executor.execute {
                val initApi = UsuariosApi()
                response = initApi.getUser(
                    binding.editTextTextEmailAddress.text.toString(),
                    binding.editTextTextPassword.text.toString()
                )
                if (response.acceso == true) {
                    println(response)
                    prefs.saveUsername("${response.nombre.toString()} ${response.apellido.toString()}")
                    prefs.saveEmail(binding.editTextTextEmailAddress.text.toString())
                    goToHome()
                } else {
                    runOnUiThread {
                        Toast.makeText(
                            this@MainActivity,
                            "Error, Contraseña o Usuario incorrecto",
                            Toast.LENGTH_LONG
                        ).show()
                        binding.btningresar.visibility = View.VISIBLE
                        binding.btnhuella.visibility = View.VISIBLE
                        binding.progressBar2.visibility = View.GONE
                    }
                }
            }
            handler.post {
                binding.btningresar.visibility = View.GONE
                binding.btnhuella.visibility = View.GONE
                binding.progressBar2.visibility = View.VISIBLE
            }
        }else{
            Toast.makeText(this@MainActivity,"Error, Contraseña o Usuario incorrecto",Toast.LENGTH_LONG).show()
        }
    }
    private fun goToHome(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}