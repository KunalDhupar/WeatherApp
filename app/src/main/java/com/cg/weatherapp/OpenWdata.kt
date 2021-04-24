package com.cg.weatherapp
data class Day(
        val name:String,
        val weather: List<Weather>,
        val main:Main
)
data class Main(
        val temp:Double,
        val feels_like:Double,
        val temp_min:Double,
        val temp_max:Double,
        val humidity: Double
)
data class Weather(
        val main:String,
        val icon:String
)

data class Climate(val daily:List<TheDay>)
data class TheDay(val dt:Long,val temp:Temp,val pressure:Int,val humidity:Int,val weather:List<Weather> )
data class Temp(val day:Double,val min:Double,val max:Double,val night:Double)



//https://api.openweathermap.org/data/2.5/onecall?lat={lat}&lon={lon}&exclude={part}&appid={API key}