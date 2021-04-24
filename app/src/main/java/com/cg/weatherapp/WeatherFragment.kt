package com.cg.weatherapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_weather_view.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A fragment representing a list of Items.
 */
class WeatherFragment : Fragment() {

    private var columnCount = 1
    var latitude:Double?=0.0
    var longitude:Double?=0.0
    lateinit var list:RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
            latitude=it.getDouble("Latitude",12.88)
            longitude=it.getDouble("Longitude",56.66)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list=view.findViewById(R.id.list)
        list.layoutManager= LinearLayoutManager(activity)
        val u="https://api.openweathermap.org/data/2.5/onecall?lat=$latitude&lon=$longitude&exclude=hourly,minutely&units=metric&appid=6a6a45e17de737c3555d54794123db48"
        val request=OpenWInterface.getInstance().getClimate(u)
        request.enqueue(popClimate())
    }
    inner class popClimate:Callback<Climate>{
        override fun onFailure(call: Call<Climate>, t: Throwable) {
            Log.d("onFailure","Failed")
        }

        override fun onResponse(call: Call<Climate>, response: Response<Climate>) {
            if (response.isSuccessful){
                val clim=response.body()
                Log.d("onSuccess","$clim")
                clim?.daily?.let {
                    list.adapter=WeatherAdapter(it){
                        val intent= Intent(activity,WeatherDetails::class.java)
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            WeatherFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}