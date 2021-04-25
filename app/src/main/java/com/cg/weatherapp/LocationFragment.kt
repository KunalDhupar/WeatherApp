package com.cg.weatherapp

import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.activity_city_location.*
import kotlinx.android.synthetic.main.fragment_location.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LocationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LocationFragment : Fragment() {
    lateinit var lmanager:LocationManager
    var currentloc: Location?=null
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }



    }
    private fun getCityName(): String {
        val gCoder= Geocoder(activity)
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
        cityName.setText(city)
    }
    private fun checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if( checkSelfPermission(activity!!,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(activity!!,android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION),1)
            }
            else{
                Toast.makeText(context,"Location permission granted..",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        lmanager= activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
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
                Toast.makeText(context, "No location found", Toast.LENGTH_LONG).show()
        }
        else
        {
            Toast.makeText(context,"Some major error", Toast.LENGTH_LONG).show()
        }
        val btn=view.findViewById<Button>(R.id.weatherB)
        btn.setOnClickListener {
//            val WFrag=ShowCurrentWeatherFragment()
//            val bundle=Bundle()
//            val cName=cityName.text.toString()
//            bundle.putString("City",cName)
//            WFrag.arguments=bundle
//            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.parentL,WFrag)
//                ?.addToBackStack(null)?.commit()
        }

        view.findViewById<Button>(R.id.climateB).setOnClickListener {
            val WeatherFrag=WeatherFragment()
            val bundle= bundleOf("Latitude" to currentloc?.latitude,"Longitude" to currentloc?.longitude)
            WeatherFrag.arguments=bundle
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.parentL,WeatherFrag)
                    ?.addToBackStack(null)?.commit()
//                val i=Intent(activity,WeatherView::class.java)
//            i.putExtra("Latitude",currentloc?.latitude)
//            i.putExtra("Longitude",currentloc?.longitude)
//            startActivity(i)


        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LocationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                LocationFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}