package com.cg.weatherapp

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_show_current_weather.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShowCurrentWeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShowCurrentWeatherFragment : Fragment() {

    var city=" "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            city=it.getString("City","Kanpur")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val key=resources.getString(R.string.apikey)
        val u="https://api.openweathermap.org/data/2.5/weather?q=$city&units=metric&appid=6a6a45e17de737c3555d54794123db48"
        val request=OpenWInterface.getInstance().getWeather(u)
        request.enqueue(CurrentWeather())
    }
    inner class CurrentWeather:Callback<Day>{
        override fun onFailure(call: Call<Day>, t: Throwable) {
            Log.d("OnFail","Failed")
        }

        override fun onResponse(call: Call<Day>, response: Response<Day>) {
            if (response.isSuccessful){
                val cw=response.body()!!
                Log.d("onResponding","$cw")
                textView4.setText(cw.name)
                textView5.setText("Current temperature: ${cw?.main?.temp_max}")
                textView6.setText("Current Humidity: ${cw?.main?.humidity}")
                val imgUrl="https://openweathermap.org/img/wn/${cw.weather[0].icon}.png"
                Glide.with(activity!!).load(Uri.parse(imgUrl)).into(iView)
                mainT.setText("Forecast: ${cw.weather[0].main}")
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_current_weather, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShowCurrentWeatherFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShowCurrentWeatherFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}