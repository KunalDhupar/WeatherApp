package com.cg.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherView : AppCompatActivity() {
    lateinit var rView: RecyclerView
     var lat:Double=0.0
    var longi:Double=0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_view)
         lat=intent.getDoubleExtra("Latitude",12.76)
         longi=intent.getDoubleExtra("Longitude",56.56)
        rView=findViewById(R.id.rView)
        rView.layoutManager= LinearLayoutManager(this)
        val u="https://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$longi&exclude=hourly,minutely&appid=6a6a45e17de737c3555d54794123db48"
        val request=OpenWInterface.getInstance().getClimate(u)
        request.enqueue(popClimate())
    }
    inner class popClimate: Callback<Climate> {
        override fun onFailure(call: Call<Climate>, t: Throwable) {
            Log.d("onFailure","Failed")
        }

        override fun onResponse(call: Call<Climate>, response: Response<Climate>) {
            if (response.isSuccessful){
                val clim=response.body()
                Log.d("onSuccess","$clim")
                clim?.daily?.let {
                    rView.adapter=WeatherAdapter(it){
                        val intent= Intent(this@WeatherView,WeatherDetails::class.java)
                        val date=it.dt
                        val mintemp=it.temp.min
                        val maxtemp=it.temp.max
                        val humidity=it.humidity
                        val pressure=it.pressure
                        val icon=it.weather[0].icon
                        val main=it.weather[0].main
                        intent.putExtra("date",date)
                        intent.putExtra("mintemp",mintemp)
                        intent.putExtra("maxtemp",maxtemp)
                        intent.putExtra("hum",humidity)
                        intent.putExtra("press",pressure)
                        intent.putExtra("icon",icon)
                        intent.putExtra("main",main)
                        startActivity(intent)
                    }

                }

            }
        }

    }
}