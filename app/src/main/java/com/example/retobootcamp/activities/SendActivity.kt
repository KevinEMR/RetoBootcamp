package com.example.retobootcamp.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.retobootcamp.R
import com.example.retobootcamp.databinding.ActivitySendBinding
import com.example.retobootcamp.services.api.DocumentosApi
import com.example.retobootcamp.services.model.NewDocument
import com.example.retobootcamp.services.model.Put
import com.example.retobootcamp.utilities.UIApplication.Companion.offices
import com.example.retobootcamp.utilities.UIApplication.Companion.prefs
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.ByteArrayOutputStream
import java.util.concurrent.Executors

class SendActivity : AppCompatActivity(), AdapterView.OnItemClickListener {
    private lateinit var binding: ActivitySendBinding
    private var ciudad: String = ""
    private lateinit var tipoDocumento : String
    private lateinit var tipoArchivo : String
    private lateinit var sImage :String
    private var cities: MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendBinding.inflate(layoutInflater)
        initUi()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val item = parent?.getItemAtPosition(position).toString()
        Toast.makeText(this@SendActivity,item,Toast.LENGTH_SHORT).show()
        ciudad = item
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                //Image Uri will not be null for RESULT_OK
                val uri: Uri = data?.data!!

                // Use Uri object instead of File to avoid storage permissions
                binding.image.visibility = View.VISIBLE
                binding.image.setImageURI(uri)
                binding.btnAdjuntar.text = getString(R.string.cambiar)
                val bitmap : Bitmap = MediaStore.Images.Media.getBitmap(contentResolver,uri)
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
                val bytes : ByteArray = stream.toByteArray()
                sImage = Base64.encodeToString(bytes,Base64.DEFAULT
                )
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun initUi(){
            for (city in offices.Items!!) {
                if (city.Ciudad in cities){
                    continue
                }else{
                cities.add(city.Ciudad!!)
            }
            }
                setContentView(binding.root)
        binding.txtEmail.setText(prefs.getEmail())
                chargePage()
    }
    private fun chargePage(){
        val adapter = ArrayAdapter(this, R.layout.list_cities, cities)
            with(binding.selectCiudad) {
            setAdapter(adapter)
            onItemClickListener = this@SendActivity
        }
        binding.btnAdjuntar.setOnClickListener {
                ImagePicker.with(this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(150)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start()
        }
        binding.btnEnviarDoc.setOnClickListener{
            binding.btnAdjuntar.visibility = View.GONE
            binding.btnEnviarDoc.visibility = View.GONE
            binding.image.visibility = View.GONE
            binding.progressBarsend.visibility = View.VISIBLE
            senddoc()
        }
        binding.radioGroupDoc.setOnCheckedChangeListener{ buttonView, Check ->
            val checkedRadioButton : RadioButton = buttonView.findViewById(Check)
            val isCheck = checkedRadioButton.isChecked
            if (isCheck){
                tipoDocumento = checkedRadioButton.text.toString()
            }
        }
        binding.radiogroupArchivo.setOnCheckedChangeListener{ buttonView, Check ->
            val checkedRadioButton : RadioButton = buttonView.findViewById(Check)
            val isCheck = checkedRadioButton.isChecked
            if (isCheck){
                tipoArchivo = checkedRadioButton.text.toString()
            }
        }
    }
    private fun senddoc(){
        val executor = Executors.newSingleThreadExecutor()
        lateinit var response : Put
            val initApi = DocumentosApi()
            if (binding.radioGroupDoc.checkedRadioButtonId != -1) {
                if (binding.txtNDoc.text.isNotEmpty()){
                    val documento = binding.txtNDoc.text.toString()
                    if (binding.txtName.text.isNotEmpty()){
                        val name = binding.txtName.text.toString()
                        if (binding.txtApellido.text.isNotEmpty()){
                            val apellido = binding.txtApellido.text.toString()
                            if (binding.txtEmail.text.isNotEmpty()){
                                val email = binding.txtEmail.text.toString()
                                if (ciudad != ""){
                                    if (binding.radiogroupArchivo.checkedRadioButtonId != -1){
                                        if (binding.btnAdjuntar.text.toString() == "Cambiar"){
                                            executor.execute{
                                            response = initApi.putDocument(NewDocument(tipoDocumento,documento,name,apellido,ciudad,email,tipoArchivo,sImage))
                                                runOnUiThread{
                                                    if(response.put!!){
                                                    Toast.makeText(this@SendActivity,"Se subio correctamente",Toast.LENGTH_LONG).show()
                                                        val intent = Intent(this, HomeActivity::class.java)
                                                        startActivity(intent)
                                                }else{
                                                        Toast.makeText(this@SendActivity,"Error",Toast.LENGTH_LONG).show()
                                                    }
                                                }
                                            }
                                        }else{
                                            Toast.makeText(this@SendActivity,"Suba el archivo",Toast.LENGTH_LONG).show()
                                        }
                                    }else{
                                        Toast.makeText(this@SendActivity,"Seleccione tipo de archivo",Toast.LENGTH_LONG).show()
                                    }
                                }else{
                                    Toast.makeText(this@SendActivity,"Seleccione la ciudad",Toast.LENGTH_LONG).show()
                                }
                            }else{
                                Toast.makeText(this@SendActivity,"Ingrese el correo",Toast.LENGTH_LONG).show()
                            }
                        }else{
                            Toast.makeText(this@SendActivity,"Ingrese el apellido",Toast.LENGTH_LONG).show()
                        }
                    }else{
                        Toast.makeText(this@SendActivity,"Ingrese el nombre",Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(this@SendActivity,"Ingrese el documento",Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this@SendActivity,"Seleccione tipo de documento",Toast.LENGTH_LONG).show()
            }
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