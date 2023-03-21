package com.example.weather

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.location.Location
import android.location.LocationListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity(), LocationListener {
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    var lon: Double = 0.0
    var lat: Double = 0.0
    var weatherForecastsHourlyModel : WeatherForecastsHourly? = null
    var DailyForecastsModel: DailyForecastsModel? = null
    var today: String? = null
    var timeHour : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getCurrent()
        getLocation()
    }
    private fun getCurrent(){
        val formatter = DateTimeFormatter.ofPattern("HH")
        today = LocalDate.now().toString()
        timeHour = LocalTime.now().format(formatter).toInt()
    }

    @SuppressLint("ResourceType")
    private fun replaceFragment() {
        val welcome: ConstraintLayout
        welcome = findViewById(R.id.welcomePage)
        welcome.visibility = View.GONE
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_contrainer, MainFragment.newInstance(lon, lat,weatherForecastsHourlyModel!!,DailyForecastsModel!!))
        transaction.commit()
    }
    private fun getLocation() {

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5f, this)
    }
    override fun onLocationChanged(location: Location) {
        lat =  13.6800 //use this for emulator (location in thailand)
            //location.latitude //use this for visual phone

        lon = 100.283 //use this for emulator (location in thailand)
            //location.longitude //use this for visual phone
     if (lon != 0.0){
         getHourlyForecasts()
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i("Permission","Granted")
            }
            else {
                Log.i("Permission","Denied")
            }
        }
    }
    private fun getHourlyForecasts(){
        val service = Retrofit.Builder()
            .baseUrl("https://data.tmd.go.th/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherForecastsAPI::class.java)

        service.getWeatherForecastsHourly(lat!!,lon!!,"tc,rh,cond",today.toString(), timeHour!!.toInt(),24)
            .enqueue(object : Callback<WeatherForecastsHourly> {

                override fun onFailure(call: Call<WeatherForecastsHourly>, t: Throwable) {
                    Log.d("TAG_", "An error happened!")
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<WeatherForecastsHourly>, response: Response<WeatherForecastsHourly>) {
                    Log.d("TAG_", response.body().toString())
                    weatherForecastsHourlyModel = response.body()
                    geteDailyForecasts()

                }
            })
    }
    private fun geteDailyForecasts(){
        val service = Retrofit.Builder()
            .baseUrl("https://data.tmd.go.th/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DailyForecastsAPI::class.java)

        service.getDailyWeatherForecasts(lat,lon,"tc_min,tc_max,cond",today.toString(), 7)
            .enqueue(object : Callback<DailyForecastsModel> {

                override fun onFailure(call: Call<DailyForecastsModel>, t: Throwable) {
                    Log.d("TAG_", "An error happened!")
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<DailyForecastsModel>, response: Response<DailyForecastsModel>) {
                    Log.d("TAG_", response.body().toString())
                    DailyForecastsModel = response.body()
                    replaceFragment()
                }
            })
    }
}