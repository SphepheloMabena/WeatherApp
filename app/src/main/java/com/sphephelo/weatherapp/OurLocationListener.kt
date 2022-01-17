package com.sphephelo.weatherapp

import android.location.Location
import android.location.LocationListener
import android.os.Bundle

class OurlocationListener : LocationListener
{
    override fun onLocationChanged(p0: Location) {
        println("Location Listener works");
        latitude=p0.latitude;
        longitude=p0.longitude;
        println("Longitude: ${longitude}");


    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        super.onStatusChanged(provider, status, extras)
    }

    override fun onProviderEnabled(provider: String) {
        super.onProviderEnabled(provider)
    }

    override fun onProviderDisabled(provider: String) {
        super.onProviderDisabled(provider)
    }
}