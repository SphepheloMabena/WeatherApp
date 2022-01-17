package com.sphephelo.weatherapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class WeatherNa:AppCompatActivity() {
     lateinit var weatherBtn: Button;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather);
        weatherBtn.setOnClickListener(View.OnClickListener {
            val intent: Intent=Intent(this,MainActivity::class.java);
            startActivity(intent);
        });

    }
}