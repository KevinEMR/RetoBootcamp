package com.example.retobootcamp.utilities

import android.app.Application
import com.example.retobootcamp.services.api.OficinasApi
import com.example.retobootcamp.services.model.Offices
import java.util.concurrent.Executors

class UIApplication : Application(){

    companion object{
        lateinit var prefs: Prefs
        lateinit var offices : Offices
    }
    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            val initApi = OficinasApi()
            offices = initApi.getOffices()
        }
    }
}