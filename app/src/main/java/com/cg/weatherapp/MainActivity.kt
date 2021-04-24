package com.cg.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add("City")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.title){
            "City"->{
//                val i= Intent(this,CityLocation::class.java)
//                startActivity(i)
                    val frag=LocationFragment.newInstance("","")
                    supportFragmentManager.beginTransaction().add(R.id.parentL,frag).commit()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}