package com.example.retobootcamp.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.retobootcamp.R
import com.example.retobootcamp.services.model.Office
import com.example.retobootcamp.utilities.UIApplication
import com.example.retobootcamp.utilities.UIApplication.Companion.offices
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class OfficeActivity : AppCompatActivity(), OnMapReadyCallback { 
    private var pERMISSIONID = 52
    private var names : MutableList<Any> = ArrayList<Any>()
    private val arrcoords : MutableList<Office> = ArrayList<Office>()
    private lateinit var map:GoogleMap
    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private lateinit var locationRequest : com.google.android.gms.location.LocationRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_office)
        supportActionBar!!.title = "Oficinas"
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            if (offices.Items!!.isNotEmpty()) {
                    getLastLocation()
            } else {
                    Toast.makeText(
                        this@OfficeActivity,
                        "Error, Intente mas tarde",
                        Toast.LENGTH_LONG
                    ).show()
        }
    }
    private fun setcityName(lat:Double,long:Double) {
        val cityname: String
        val countryname: String
        val geocoder = Geocoder(this, Locale.getDefault())
        val addres = geocoder.getFromLocation(lat,long,1)
        cityname = addres[0].locality
        countryname = addres[0].countryName
        names.add(0,cityname)
        names.add(1,countryname)
        names.add(2,lat)
        names.add(3,long)
    }
    private fun checkpermission() : Boolean{
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        return true
    }
    private fun requestpermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),pERMISSIONID)
            getLastLocation()
        }
    }
    private fun isLocationEnable():Boolean{
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
    private fun getLastLocation(){
        if(checkpermission()){
            if (isLocationEnable()){
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestpermission()
                }else{
                    fusedLocationProviderClient.lastLocation.addOnCompleteListener{ task ->
                        val location = task.result
                        if (location == null){
                            getNewLocation()
                        }else{
                            val lat = location.latitude
                            val long = location.longitude
                            setcityName(lat,long)
                            createFragment()
                        }
                    }
                }
            }else{
                Toast.makeText(this,"Activar permisos",Toast.LENGTH_LONG).show()
            }
        }else{
            requestpermission()
        }
    }

    private fun getNewLocation(){
        locationRequest = com.google.android.gms.location.LocationRequest.create()
        locationRequest.priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 2

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestpermission()
        }else{
            fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallBack, Looper.myLooper()!!)
        }
    }
    private val locationCallBack = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            val laslocation = p0.lastLocation
            val lat = laslocation.latitude
            val long = laslocation.longitude
            setcityName(lat,long)
            createFragment()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == pERMISSIONID){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Debug","Tienes el permiso")
            }
        }
    }
    private fun createFragment(){
        for(oficina in offices.Items!!){
            if(oficina.Ciudad!! == names[0] || oficina.Ciudad == names[1]){
                arrcoords.add(oficina)
            }else{
                continue
            }

        }
        val mapFragment:SupportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    override fun onMapReady(googleMap:GoogleMap){
        map = googleMap
        createMarker()
    }
    private fun createMarker() {
        lateinit var coords : LatLng
        for(oficina in arrcoords){
        coords = LatLng(oficina.Latitud!!.toDouble(), oficina.Longitud!!.toDouble())
        val marker: MarkerOptions = MarkerOptions().position(coords).title(oficina.Nombre)
        map.addMarker(marker)
        }
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(LatLng(names[2] as Double, names[3] as Double),11f),
            3000,
            null
        )
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
        UIApplication.prefs.wipe()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}