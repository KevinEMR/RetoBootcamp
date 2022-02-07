package com.example.retobootcamp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retobootcamp.R
import com.example.retobootcamp.databinding.ActivitySeeBinding
import com.example.retobootcamp.services.api.DocumentosApi
import com.example.retobootcamp.services.model.Documents
import com.example.retobootcamp.utilities.RecyclerAdapter
import com.example.retobootcamp.utilities.UIApplication.Companion.prefs
import java.util.concurrent.Executors

class SeeActivity : AppCompatActivity(),RecyclerAdapter.OnItemClick{
    private lateinit var binding: ActivitySeeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
    }

    override fun onClick(adjunto: String?, context: Context) {
        println("metodo click entro")
        val intent = Intent(this@SeeActivity,ImageDetailActivity::class.java)
        prefs.saveAdjunto(adjunto!!)
        startActivity(intent)
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
        supportActionBar!!.title = "Ver Documentos"
        setupRecyclerView()
    }
    private fun setupRecyclerView(){
        val executor = Executors.newSingleThreadExecutor()
        val listadjunto : MutableList<String> = ArrayList()
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.setHasFixedSize(true)
        val initApi = DocumentosApi()
        executor.execute{
            val listdocuments : Documents = initApi.getDocuments(correo = prefs.getEmail())
            for(document in listdocuments.Items!!){
                val documentApi = initApi.getDocuments(idRegistro = document.IdRegistro)
                val image = documentApi.Items!![0].Adjunto
                listadjunto.add(image!!)
            }
            runOnUiThread{
                println(listadjunto.size)
                binding.recyclerview.adapter = RecyclerAdapter(this, listdocuments.Items,listadjunto,this)
            }
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