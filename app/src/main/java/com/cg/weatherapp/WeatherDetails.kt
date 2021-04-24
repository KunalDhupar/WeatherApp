package com.cg.weatherapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_weather_details.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class WeatherDetails : AppCompatActivity() {
    lateinit var dDate:TextView
    lateinit var rHumidity:TextView
    lateinit var rPressure:TextView
    lateinit var iv:ImageView
    lateinit var Desc:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_details)
        val min=intent.getDoubleExtra("mintemp",0.0)
        val max=intent.getDoubleExtra("maxtemp",0.0)
        val dt=intent.getLongExtra("date",0)
        val hum=intent.getIntExtra("hum",0)
        val press=intent.getIntExtra("press",0)
        val icon=intent.getStringExtra("icon")
        val main=intent.getStringExtra("main")
        dDate=findViewById(R.id.dDate)
        rHumidity=findViewById(R.id.rHumidity)
        rPressure=findViewById(R.id.rPressure)
        Desc=findViewById(R.id.rMain)
        iv=findViewById(R.id.imgView)
        dDate.text=convertLongToTime(dt,"EEE,dd-MMM")
        rHumidity.text="Humidity: $hum"
        rPressure.text="Pressure: $press"
        rMain.text="Forecast: $main"
        val imgUrl="https://openweathermap.org/img/wn/${icon}.png"
        Glide.with(this).load(Uri.parse(imgUrl)).into(iv)

    }


        fun convertLongToTime (time: Long,pattern: String): String {
            val c = Calendar.getInstance();
            c.setTimeInMillis(time*1000);

            val date: Date = c.getTime()
            val format1 = SimpleDateFormat(pattern)

            // Calendar.DAY_OF_WEEK
            return  format1.format(date)
        }

    }
