package com.sphephelo.weatherapp

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LocationOff:AppCompatActivity() {

    //this screen is responsible for prompting user to enable location
    lateinit var btn:Button;
    lateinit var tv:TextView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.location);
        tv=findViewById(R.id.infom)
        btn=findViewById(R.id.allow_permission);
        btn.setOnClickListener(View.OnClickListener {
            if(btn.text =="Continue")
            {
                val intent:Intent=Intent(this@LocationOff,MainActivity::class.java);
                startActivity(intent);

            }
            else
            {
                btn.setText("Continue");
                tv.setText("Proceed to weather screen")
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
        })

    }
}