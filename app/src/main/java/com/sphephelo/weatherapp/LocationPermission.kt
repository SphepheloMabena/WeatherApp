package com.sphephelo.weatherapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class LocationPermission: AppCompatActivity() {
    //this screen is responsible for prompting user to grant permissions

    lateinit var permBtn:Button;

     fun checkPermission(permission: String, requestCode: Int) {

        if (ContextCompat.checkSelfPermission(this@LocationPermission, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(this@LocationPermission, arrayOf(permission), requestCode)
        } else {
            Toast.makeText(this@LocationPermission, "Permission already granted", Toast.LENGTH_SHORT).show()
            val intent:Intent=Intent(this@LocationPermission,MainActivity::class.java);
            startActivity(intent);

        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this@LocationPermission,
                            Manifest.permission.ACCESS_FINE_LOCATION) ===
                                PackageManager.PERMISSION_GRANTED)) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                        val intent:Intent=Intent(this,MainActivity::class.java);
                        startActivity(intent);

                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.permission);
        permBtn=findViewById(R.id.allow_permission);

        permBtn.setOnClickListener(View.OnClickListener {
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION,1);
            checkPermission(Manifest.permission.ACCESS_FINE_LOCATION,1);


        });
    }
}