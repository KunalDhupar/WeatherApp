package com.cg.weatherapp

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface OpenWInterface {
    @GET
    fun getWeather(@Url url:String):Call<Day>

    @GET
    fun getClimate(@Url url: String):Call<Climate>

    companion object{
        val BASE_URL="http://api.openweathermap.org/"
        fun getInstance():OpenWInterface{
            val builder= Retrofit.Builder()
            builder.addConverterFactory(GsonConverterFactory.create())
            builder.baseUrl(BASE_URL)
            val retrofit=builder.build()
            return retrofit.create(OpenWInterface::class.java) //all api code gets generated
        }
    }
}



//api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}