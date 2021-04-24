package com.cg.weatherapp

import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_city_location.*

class CityLocation : AppCompatActivity() {
    lateinit var lmanager:LocationManager
    var currentloc:Location?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_location)
        checkPermission()
        lmanager=getSystemService(Context.LOCATION_SERVICE) as LocationManager
        //locatiuon provider
        val providerlist=lmanager.getProviders(true)
        var providername:String=""
        if (providerlist.isNotEmpty()) {
            if (providerlist.contains(LocationManager.GPS_PROVIDER)) {
                providername = LocationManager.GPS_PROVIDER
            } else if (providerlist.contains(LocationManager.NETWORK_PROVIDER)) {
                providername = LocationManager.NETWORK_PROVIDER

            } else
                providername = providerlist[0]


            val loc = lmanager.getLastKnownLocation(providername)




            if (loc != null) {
                updateLocation(loc)
            } else
                Toast.makeText(this, "No location found", Toast.LENGTH_LONG).show()
        }
        else
        {
            Toast.makeText(this,"Some major error",Toast.LENGTH_LONG).show()
        }

    }
    private fun getCityName(): String {
        val gCoder= Geocoder(this)
        val addList=gCoder.getFromLocation(currentloc?.latitude!!,currentloc?.longitude!!,10)
        if(addList.isNotEmpty()){
            val addr=addList[0]
            var strAddr=""
            strAddr=addr.locality
            return strAddr
        }
        else{
        return ""}
    }

    private fun updateLocation(loc: Location) {
        val latt=loc.latitude
        val longi=loc.longitude
        var dist=0f
        if(currentloc!=null){
            dist= currentloc?.distanceTo(loc)!!
        }
        currentloc = loc
        val city=getCityName()
        cityN.setText(city)
    }

    private fun checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if( checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION),1)
            }
            else{
                Toast.makeText(this,"Location permission granted..",Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults.isNotEmpty()){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Location permsission granted",Toast.LENGTH_LONG).show()
            }
            else{
                finish()
            }
        }
    }
}