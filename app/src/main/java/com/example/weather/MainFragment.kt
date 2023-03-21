package com.example.weather

import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class MainFragment : Fragment(){
    var lon :Double = 0.0
    var lat :Double = 0.0
    var tvLocation: TextView? = null
    var imvWeather: ImageView? = null
    var tvTemp: TextView? = null
    var tvDes: TextView? = null
    var bgImage: ConstraintLayout? = null
    var weatherForecastsHourlyModel : WeatherForecastsHourly? = null
    var dailyForecastsModel: DailyForecastsModel? = null
    var hourlyData: Data? = null
    var location: String? = null

    var timearr : ArrayList<String?> = ArrayList()
    var temparr: ArrayList<Double?> = ArrayList()
    var condarr : ArrayList<String?> = ArrayList()
    var image: ArrayList<Int?>  = ArrayList()

    var dayarr : ArrayList<String?> = ArrayList()
    var minTC: ArrayList<Double?> = ArrayList()
    var maxTC: ArrayList<Double?> = ArrayList()
    var dailyCond: ArrayList<String?> = ArrayList()
    var dailyImage: ArrayList<Int?>  = ArrayList()

    var rcvHourly: RecyclerView? = null
    var rcvDaily: RecyclerView? = null

    var des: String? = null
    var wheatherImage: Int? =null

    companion object {
        @JvmStatic
        fun newInstance(lon: Double, lat: Double, weatherForecastsHourlyModel: WeatherForecastsHourly, dailyForecastsModel: DailyForecastsModel) = MainFragment().apply {
            this.lon = lon
            this.lat = lat
            this.weatherForecastsHourlyModel = weatherForecastsHourlyModel
            this.dailyForecastsModel = dailyForecastsModel
            hourlyData = weatherForecastsHourlyModel.WeatherForcasts.get(0).Forecasts?.get(0)?.data
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        hourlyData()
        DailyData()
        initRecyclerView()
        initRCVDaily()
    }

    private fun initRecyclerView(){
        rcvHourly = view?.findViewById(R.id.rcv_hourly) as RecyclerView
       rcvHourly!!.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        val myAdapter = Adapter(timearr,temparr,condarr,image)
        rcvHourly!!.setAdapter(myAdapter)
    }
    private fun initRCVDaily(){
        rcvDaily = view?.findViewById(R.id.rcv_7day) as RecyclerView
        rcvDaily!!.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        val dailyAdapter = ForecastsAdapter(dayarr,minTC,maxTC,dailyCond,dailyImage)
        rcvDaily!!.setAdapter(dailyAdapter)
    }

    private fun initView(){
        tvLocation = requireView().findViewById(R.id.tv_location)
        imvWeather = requireView().findViewById(R.id.imv_ic_weather)
        tvTemp = requireView().findViewById(R.id.tv_temp)
        tvDes = requireView().findViewById(R.id.tv_description)
        bgImage = requireView().findViewById(R.id.bgMain)
        getLocation(lat,lon)
        setDescription(hourlyData?.cond!!.toInt())

        tvTemp!!.text =  hourlyData?.tc.toString()+"°"
        tvLocation!!.text = location.toString()
        tvDes!!.text = des
        initImage()

    }

    private fun initImage(){
        val formatter = DateTimeFormatter.ofPattern("HH")
        val timeHour = LocalTime.now().format(formatter).toInt()
        if (timeHour <= 5 ){
            setImageNight(hourlyData?.cond!!.toInt())
            bgImage?.setBackgroundResource(R.drawable.bg_day)
        }else if(timeHour >= 19){
            setImageNight(hourlyData?.cond!!.toInt())
            bgImage?.setBackgroundResource(R.drawable.bg_night)
        }else{
            setImageDay(hourlyData?.cond!!.toInt())
        }
        imvWeather?.setImageResource(wheatherImage!!)
    }

    private fun setDescription(cond: Int): String{
      when(cond){
          1-> des= getString(R.string.cond1)
          2-> des = getString(R.string.cond2)
          3-> des = getString(R.string.cond3)
          4-> des = getString(R.string.cond4)
          5-> des = getString(R.string.cond5)
          6-> des= getString(R.string.cond6)
          7-> des = getString(R.string.cond7)
          8-> des = getString(R.string.cond8)
          9-> des = getString(R.string.cond9)
          10-> des = getString(R.string.cond10)
          11-> des = getString(R.string.cond11)
          12-> des = getString(R.string.cond12)
      }
        return des.toString()
    }

    private fun setImageDay(cond: Int): Int{
        when(cond){
            1-> wheatherImage = R.drawable.sun
            2-> wheatherImage = R.drawable.suncloudy
            3-> wheatherImage = R.drawable.cloud
            4-> wheatherImage = R.drawable.cloud
            5-> wheatherImage = R.drawable.rain
            6-> wheatherImage= R.drawable.cloud_plus
            7-> wheatherImage = R.drawable.raining
            8-> wheatherImage = R.drawable.storm
            9-> wheatherImage = R.drawable.snowflake
            10-> wheatherImage = R.drawable.cold
            11-> wheatherImage = R.drawable.cold
            12-> wheatherImage = R.drawable.hot
        }
        return wheatherImage!!
    }

    private fun setImageNight(cond: Int): Int{
        when(cond){
            1-> wheatherImage = R.drawable.moon
            2-> wheatherImage = R.drawable.mooncloudy
            3-> wheatherImage = R.drawable.cloud
            4-> wheatherImage = R.drawable.cloud
            5-> wheatherImage = R.drawable.rain
            6-> wheatherImage= R.drawable.cloud_plus
            7-> wheatherImage = R.drawable.raining
            8-> wheatherImage = R.drawable.storm
            9-> wheatherImage = R.drawable.snowflake
            10-> wheatherImage = R.drawable.cold
            11-> wheatherImage = R.drawable.cold
            12-> wheatherImage = R.drawable.hot
        }
        return wheatherImage!!
    }


    private fun getLocation(latitude: Double, longitude: Double){

        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses != null) {
                var amphoe = addresses[0].subAdminArea
                amphoe = amphoe.replace("Amphoe", "")
                amphoe = amphoe .replace("District", "\n")
                var province = addresses[0].adminArea
                province = province.replace("Chang Wat", ",")
                location = amphoe + province
            } else {
                Log.w("My Current loction address", "No Address returned!")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.w("My Current loction address", "Canont get Address!")
        }
    }

    private fun hourlyData(){
        weatherForecastsHourlyModel?.WeatherForcasts!![0].Forecasts.forEach {
            timearr.add(it.Time.hours.toString() + ".00")
            temparr.add(it.data.tc)
            val condDes = setDescription(it.data.cond)
            condarr.add(condDes)
            var condImv = setImageDay(it.data.cond)
            if ((it.Time.hours <= 5 || it.Time.hours >= 19)){
                condImv = setImageNight(it.data.cond)
            }
            image.add(condImv)
            }
    }

    @SuppressLint("SimpleDateFormat")
    var DayOfWeek: SimpleDateFormat = SimpleDateFormat("EEEE")

    private fun DailyData(){
        dailyForecastsModel?.DailyWeatherForcasts!![0].DailyForecasts.forEach{
            if (DayOfWeek.format(it.DailyTime).toString().uppercase() == LocalDate.now().dayOfWeek.toString()){
                dayarr.add("Today")
            }else{
                dayarr.add(DayOfWeek.format(it.DailyTime).toString())
            }
            minTC.add(it.Dailydata.mintc)
            maxTC.add(it.Dailydata.maxtc)
            val cond = setDescription(it.Dailydata.cond)
            dailyCond.add(cond)
            val condImg = setImageDay(it.Dailydata.cond)
            dailyImage.add(condImg)
        }
    }

}



