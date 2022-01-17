package com.sphephelo.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle





import android.Manifest
import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.location.LocationListener

import android.location.LocationManager
import android.os.Build

import android.provider.Settings
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.ui.AppBarConfiguration

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode

import com.sphephelo.weather.ui.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt



var msg:String = ""
var latitude:Double = 0.0;
var longitude:Double=0.0;



//Code by Sphephelo Mabena
class MainActivity : AppCompatActivity() {



    var text = "Hellow"
    //declaring the valuable
    lateinit var cur_temp: TextView
    lateinit var current_temperature: TextView//current temperature
    lateinit var max_temp: TextView//maximum temperature
    lateinit var min_temp: TextView//minimum temperature for the location
    lateinit var description: TextView//describes the weather
    lateinit var background: ConstraintLayout ;//this is the weather background

    var weekDays: ArrayList<String> = ArrayList<String>();
    var icons: ArrayList<Int> = ArrayList<Int>();

    lateinit var recAdapter: CustomAdapter;

    lateinit var reycleView: RecyclerView;

    var minTemp: ArrayList<Double> = ArrayList<Double>(5)//array of the minimum temperatires recieved
    var maxTemp: ArrayList<Int> = ArrayList<Int>(5)
    var weatherDescription: ArrayList<String> = ArrayList<String>(5)


    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var locationManager: LocationManager;//we need the users location
    var hasGPS = false;
    var hasNetwork = false;

    private fun checkPermission(permission: String, requestCode: Int) {

        if (ContextCompat.checkSelfPermission(this@MainActivity, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
        } else {
            Toast.makeText(this@MainActivity, "Permission already granted", Toast.LENGTH_SHORT).show()
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0F,OurlocationListener() );


            /*val localGpsLocation: Location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDERthis) as Location
            if (localGpsLocation != null)
                println("Location : $localGpsLocation")
            latitude = localGpsLocation.latitude
            longitude = localGpsLocation.longitude;

            println("Latitude up: ${localGpsLocation.latitude}");
            println("Longitude up: ${localGpsLocation.longitude}");*/
        }
    }

    //Code by Sphephelo Mabena
    private fun getLocation() {
        //I used Location Manager instead of the new fused location provider
        //because Fused Location Provider is depedent on Google Play Services
        //Hence it will not be compatible with
        // Android Devices without Google Play Services (e.g Huawei )
        //Meaning the App wouldn't have worked properly on Huawei Devices
        //if we had used Fused Location Provider
        msg = "Active"

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (hasGPS || hasNetwork) {


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                val permissions:Array<String> =arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION);
                checkPermission(Manifest.permission.ACCESS_FINE_LOCATION,1);//check for fine location
                checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION,1)
                ;

            }
            else{
//                    val localGpsLocation: Location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) as Location
//                    if (localGpsLocation != null)
//                        println("Location : $localGpsLocation")
//                    latitude = localGpsLocation.latitude
//                    longitude = localGpsLocation.longitude;
//
//                    println("Latitude up: ${localGpsLocation.latitude}");
//                    println("Longitude up: ${localGpsLocation.longitude}");

            }
            //not neccessary
            /*locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0F, object : LocationListener {
                override fun onLocationChanged(p0: Location) {
                    if (p0 != null) {
                        println("Latitude: ${p0.latitude}");

                    }
                }

            })*/




        } else {
            //if Location is disabled
            //Location Off Screen will be opened
            val intent:Intent=Intent(this,LocationOff::class.java);
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
                    if ((ContextCompat.checkSelfPermission(this@MainActivity,
                            Manifest.permission.ACCESS_FINE_LOCATION) ===
                                PackageManager.PERMISSION_GRANTED)) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                        getLocation();
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                    //if Permission is denied
                    //Open LocationPermission Screen will be opened
                    //this screen will prompt the user to grant permission
                    val intent:Intent=Intent(this,LocationPermission::class.java);
                    startActivity(intent);
                }
                return
            }
        }
    }
    //gets the focast data from the api
    @RequiresApi(Build.VERSION_CODES.O)
    fun startForecast(urlForcast:String): Boolean {
        weekDays = ArrayList<String>();
        icons = ArrayList<Int>();
        var currentDay= LocalDate.now().dayOfWeek.value
        var x = currentDay;

        val weather = WeatherInstance.weatherInterface.WeatherInfom(urlForcast);
        weather.enqueue(object : Callback<WeatherInfo> {
            override fun onResponse(
                call: Call<WeatherInfo>,
                response: Response<WeatherInfo>
            ) {
                val weatherInfo: WeatherInfo? = response.body();
                if (weatherInfo != null) {
                    println("Size of Array: ${weatherInfo.list.size}")//size = 40;

                    //data for the 5 days

                    val firstDay: X = weatherInfo.list[3];
                    val secondDay: X = weatherInfo.list[11];
                    val thirdDay: X = weatherInfo.list[19];
                    val fourthDay: X = weatherInfo.list[27];
                    val fithDay: X = weatherInfo.list[35];


                    weatherDescription.add(firstDay.weather[0].main);
                    weatherDescription.add(secondDay.weather[0].main);
                    weatherDescription.add(thirdDay.weather[0].main);
                    weatherDescription.add(fourthDay.weather[0].main);
                    weatherDescription.add(fithDay.weather[0].main);

                    //Array List with min temperatures
                    // This Array List will be used in the recycle view


                    minTemp.add(firstDay.main.temp_min - 273);
                    minTemp.add(secondDay.main.temp_min - 273);
                    minTemp.add(thirdDay.main.temp_min - 273);
                    minTemp.add(fourthDay.main.temp_min - 273);
                    minTemp.add(fithDay.main.temp_min - 273);

                    //Array List with max temperatures
                    // This Array List will be used in the recycle view


                    maxTemp.add(firstDay.main.temp_max.roundToInt() - 273)
                    maxTemp.add(secondDay.main.temp_max.roundToInt() - 273)
                    maxTemp.add(thirdDay.main.temp_max.roundToInt() - 273);
                    maxTemp.add(fourthDay.main.temp_max.roundToInt() - 273);
                    maxTemp.add(fithDay.main.temp_max.roundToInt() - 273);

                    //Array List with min temperatures
                    // This Array List will be used in the recycle view


                    println("Description $weatherDescription")



                    println("Weather Description : $weatherDescription")
                    if (weatherDescription.size == 5) {
                        println("Complete");
                        for (description in weatherDescription) {
                            println("Size ${weatherDescription.size}")
                            println("Yes")
                            when (description) {
                                "Sunny" -> icons.add(R.drawable.partlysunny);
                                "Clouds" -> icons.add(R.drawable.rain);
                                "Clear" -> icons.add(R.drawable.partlysunny);
                                "Rain" -> icons.add(R.drawable.rain);

                            }
                        }
                    }


                    //Array List storing weekdays

                    for (temperature in minTemp) {
                        if (x < 7) {
                            x += 1;
                        } else if (x == 7) {
                            x = 1
                        }
//Code by Sphephelo Mabena
                        when (x) {
                            1 -> weekDays.add("Monday")
                            2 -> weekDays.add("Tuesday")
                            3 -> weekDays.add("Wednesday")
                            4 -> weekDays.add("Thursday")
                            5 -> weekDays.add("Friday")
                            6 -> weekDays.add("Saturday")
                            7 -> weekDays.add("Sunday")
                        }
                        // x += 1;
                    }



                    recAdapter = CustomAdapter(maxTemp, weekDays, icons);

                    reycleView = findViewById(R.id.reycleview);
                    reycleView.layoutManager = LinearLayoutManager(applicationContext);

                    reycleView.adapter = recAdapter


                    //println("Weather info $weatherInfo")

                }
                else{
                    val intent:Intent=Intent(this@MainActivity,WeatherNa::class.java);
                    startActivity(intent);


                }

            }

            override fun onFailure(call: Call<WeatherInfo>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show();

                println("Error msg: ${t.message}")
            }

        })
        if(urlForcast =="")
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    fun getCurrentWeather(urlCurrent:String)
    {
        var minmum_temp: Int = 0;

        var maximum_temp: Int = 0;
        var current_temp: Int = 0;
        var current_description: String = "";


        minTemp= ArrayList<Double>(5)
        maxTemp = ArrayList<Int>(5)
        weatherDescription= ArrayList<String>(5)


        println("Mini: $minmum_temp")


        val currentWeather = CurrentWeatherInstance.currentWeatherInterface.CurrentWeatherInfom(urlCurrent);
        currentWeather.enqueue(object : Callback<Current> {
            @SuppressLint("Range")
            override fun onResponse(call: Call<Current>, response: Response<Current>) {
                val currentWeatherInfo: Current? = response.body();
                println("Current Weather Data : $currentWeatherInfo")
                if (currentWeatherInfo != null) {
                    //converts temperature from kelvins to degrees celcuis
                    println("")
                    minmum_temp = currentWeatherInfo.main.temp_min.toInt() - 273;
                    current_temp = currentWeatherInfo.main.temp.toInt() - 273;
                    maximum_temp = currentWeatherInfo.main.temp_max.toInt() - 273;
                    current_description = currentWeatherInfo.weather[0].main;

                    println("Current Description :$current_description")

                    //background changees

                    when (current_description) {
                        "Clouds" -> background.background = ResourcesCompat.getDrawable(resources, R.drawable.cloudy_background, null)
                        "Rain" -> background.background = ResourcesCompat.getDrawable(resources, R.drawable.rainy_background, null)
                        "Sunny" -> background.background = ResourcesCompat.getDrawable(resources, R.drawable.sunny_background, null)
                        "Clear" -> background.background = ResourcesCompat.getDrawable(resources, R.drawable.sunny_background, null)

                    }
                    val actionBar: ActionBar? = getActionBar();
                    when (current_description) {
                        "Clouds" -> if (actionBar != null) {
                            val colorDrawable: ColorDrawable = ColorDrawable(Color.parseColor("#54717A"));
                            actionBar.setBackgroundDrawable(colorDrawable)
                        }
                        "Rain" -> if (actionBar != null) {
                            val colorDrawable: ColorDrawable = ColorDrawable(Color.parseColor("#57575D"));
                            actionBar.setBackgroundDrawable(colorDrawable)
                        }
                        "Sunny" -> if (actionBar != null) {
                            val colorDrawable: ColorDrawable = ColorDrawable(Color.parseColor("#47AB2F"));
                            actionBar.setBackgroundDrawable(colorDrawable)
                        }
                        "Clear" -> if (actionBar != null) {
                            val colorDrawable: ColorDrawable = ColorDrawable(Color.parseColor("#47AB2F"));
                            actionBar.setBackgroundDrawable(colorDrawable)
                        }
//Code by Sphephelo Mabena

                    }

                    println("Minimum temperature : $minmum_temp")
                    cur_temp.text = "${current_temp.toString()} \u2103"
                    current_temperature.text = "${current_temp.toString()} \u2103";
                    max_temp.text = "${maximum_temp.toString() } \u2103";
                    min_temp.text = "${minmum_temp.toString()} \u2103";
                    description.text = current_description;

                }


            }



            override fun onFailure(call: Call<Current>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show();

                println("Error msg: ${t.message}")
            }

        }
        )
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getLocation();

        cur_temp= findViewById(R.id.cur_temp);
        current_temperature= findViewById(R.id.current_temp);
        max_temp= findViewById(R.id.max_temp);
        min_temp= findViewById(R.id.min_temp);
        description = findViewById(R.id.description);
        background = findViewById(R.id.background)








        startForecast("https://api.openweathermap.org/data/2.5/forecast?lat=${latitude}&lon=${longitude}&appid=94b7c8421c8f5599bc09de7cdd301ca1");
        getCurrentWeather("https://api.openweathermap.org/data/2.5/weather?lat=${latitude}&lon=${longitude}&appid=94b7c8421c8f5599bc09de7cdd301ca1");











//Code by Sphephelo Mabena
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Location Added to favourites", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        // Initialize the SDK
        Places.initialize(applicationContext, "AIzaSyBUmGMHXv6Wjb_36tcPhOJ1n6ob9N30vkg")

        // Create a new PlacesClient instance
        val placesClient = Places.createClient(applicationContext)



        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
                supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                        as AutocompleteSupportFragment

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onError(p0: Status) {

                Log.i(ContentValues.TAG, "An error occurred: $p0")
            }

            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                Log.i(ContentValues.TAG, "Place: ${place.name}, ${place.id}, ${place.latLng.latitude}")
                println("Latitude: ${place.latLng.latitude}")
            }


        })

        val AUTOCOMPLETE_REQUEST_CODE = 1

        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        val fields = listOf(Place.Field.ID, Place.Field.NAME)

        val search:ImageButton=findViewById(R.id.search);
        search.setOnClickListener()
        {
            println("Pressed")
            autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
                override fun onError(p0: Status) {
                    TODO("Not yet implemented")
                    Log.i(ContentValues.TAG, "An error occurred: $p0")
                }

                override fun onPlaceSelected(place: Place) {
                    // TODO: Get info about the selected place.
                    Log.i(ContentValues.TAG, "Place: ${place.name}, ${place.id}")
                   // println("Latitude: ${place.latLng.latitude}")





                    startForecast("https://api.openweathermap.org/data/2.5/forecast?q=${place.name}&appid=94b7c8421c8f5599bc09de7cdd301ca1");
                    getCurrentWeather("https://api.openweathermap.org/data/2.5/weather?q=${place.name}&appid=94b7c8421c8f5599bc09de7cdd301ca1");


                }


            })

            val AUTOCOMPLETE_REQUEST_CODE = 1

            // Set the fields to specify which types of place data to
            // return after the user has made a selection.
            val fields = listOf(Place.Field.ID, Place.Field.NAME)

            // Start the autocomplete intent.
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(applicationContext)
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
        }

        // Start the autocomplete intent.
       /* val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(applicationContext)
       startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)

*/
//Code by Sphephelo Mabena


    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        Log.i(ContentValues.TAG, "Place: ${place.name}, ${place.id}} ")
                        //latitude=place.latLng.latitude
                        val source:TextView=findViewById(R.id.source)
                        source.text="${place.name}";
                        startForecast("https://api.openweathermap.org/data/2.5/forecast?q=${place.name}&appid=94b7c8421c8f5599bc09de7cdd301ca1");
                        getCurrentWeather("https://api.openweathermap.org/data/2.5/weather?q=${place.name}&appid=94b7c8421c8f5599bc09de7cdd301ca1");
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        Log.i(ContentValues.TAG, status.statusMessage.toString())
                    }
                }//Code by Sphephelo Mabena
                Activity.RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
            return
        }
       super.onActivityResult(requestCode, resultCode, data)
    }
}





