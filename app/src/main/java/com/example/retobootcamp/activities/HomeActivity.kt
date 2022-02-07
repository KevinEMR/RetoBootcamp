package com.example.retobootcamp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.retobootcamp.R
import com.example.retobootcamp.databinding.ActivityHomeBinding
import com.example.retobootcamp.utilities.UIApplication.Companion.prefs

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_enviar -> goToSend()
            R.id.menu_ver -> goToSee()
            R.id.menu_oficianas -> goToOffice()
            R.id.menu_salir -> salir()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initUi(){
        binding.btnEnviar.setOnClickListener {goToSend()
        }
        binding.btnVer.setOnClickListener {goToSee()
        }
        binding.btnOficinas.setOnClickListener {goToOffice()
        }
        val username = prefs.getUsername()
        supportActionBar!!.title = username
    }

    private fun goToSend(){
        val intent = Intent(this, SendActivity::class.java)
        startActivity(intent)
    }

    private fun goToSee(){
        val intent = Intent(this, SeeActivity::class.java)
        startActivity(intent)
    }

    private fun goToOffice(){
        val intent = Intent(this, OfficeActivity::class.java)
        startActivity(intent)
    }

    private fun salir(){
        prefs.wipe()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}