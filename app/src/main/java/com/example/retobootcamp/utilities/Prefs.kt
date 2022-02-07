package com.example.retobootcamp.utilities

import android.content.Context

class Prefs (val context: Context){

    private val sHAREDNAME = "login_shared"
    private val sHAREDUSERNAME = "username"
    private val sHAREDEMAIL = "email"
    private val sHAREDADJUNTO = "adjunto"

    private val storage = context.getSharedPreferences(sHAREDNAME,0)

    fun saveUsername(name : String){
        storage.edit().putString(sHAREDUSERNAME,name).apply()
    }

    fun saveAdjunto(adjunto : String){
        storage.edit().putString(sHAREDADJUNTO,adjunto).apply()
    }

    fun saveEmail(email : String){
        storage.edit().putString(sHAREDEMAIL,email).apply()
    }

    fun getUsername() : String{
        return storage.getString(sHAREDUSERNAME,"")!!
    }

    fun getAdjunto() : String{
        return storage.getString(sHAREDADJUNTO,"")!!
    }

    fun getEmail() : String{
        return storage.getString(sHAREDEMAIL,"")!!
    }

    fun wipe(){
        storage.edit().clear().apply()
    }
}