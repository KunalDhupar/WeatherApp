package com.cg.weatherapp

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class WeatherAdapter(val cList:List<TheDay>,val lstner:(TheDay)->Unit): RecyclerView.Adapter<WeatherAdapter.ViewHolder>(){
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val iv=view.findViewById<ImageView>(R.id.iv)
        val title=view.findViewById<TextView>(R.id.cityTitle)
        val date=view.findViewById<TextView>(R.id.rDate)
        val temp_min=view.findViewById<TextView>(R.id.rTempMin)
        val temp_max=view.findViewById<TextView>(R.id.rTempMax)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflator= LayoutInflater.from(parent.context)
        val v=inflator.inflate(R.layout.fragment_item,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int =cList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val climate=cList[position]
        val dt=convertLongToTime(climate.dt,"EEE,dd-MMM")

        holder.date.text= "Date: $dt"
        holder.temp_max.text="Max Temp: ${climate.temp.max}°C"
        holder.temp_min.text="Min Temp:${climate.temp.min}°C"
        holder.title.text="Kanpur"
        val imgUrl="https://openweathermap.org/img/wn/${climate.weather[0].icon}.png"
        Glide.with(holder.itemView.context).load(Uri.parse(imgUrl)).into(holder.iv)
        holder.itemView.setOnClickListener {
            lstner(climate)
        }
    }
    fun convertLongToTime (time: Long,pattern: String): String {
        val c = Calendar.getInstance();
        c.setTimeInMillis(time*1000);

        val date: Date = c.getTime()
        val format1 = SimpleDateFormat(pattern)


        return  format1.format(date)
    }
}