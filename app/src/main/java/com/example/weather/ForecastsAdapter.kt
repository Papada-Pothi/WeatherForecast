package com.example.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList


class ForecastsAdapter(
    var dayarr: ArrayList<String?> = ArrayList(),
    var minTC: ArrayList<Double?> = ArrayList(),
    var maxTC: ArrayList<Double?> = ArrayList(),
    var dailyCond: ArrayList<String?> = ArrayList(),
    var dailyImage: ArrayList<Int?>
)  : RecyclerView.Adapter<ForecastsViewHolder>() {

        override fun getItemCount(): Int =  dayarr.size

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ForecastsViewHolder {
            val view = ForecastsViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.forecastsitem, p0, false))
            return view
        }


    override fun onBindViewHolder(holder: ForecastsViewHolder, position: Int) {
        holder.tvDay?.text = dayarr.get(position).toString()
        holder.tvMin?.text = minTC.get(position).toString()+"°"
        holder.tvMax?.text = maxTC.get(position).toString()+"°"
        holder.tvCond?.text = dailyCond.get(position)
        val img =dailyImage.get(position)
        holder.imvIcon?.setImageResource(img!!)
    }

    }

    class ForecastsViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        var tvDay: TextView? = null
        var imvIcon: ImageView? = null
        var tvMin: TextView? = null
        var tvMax: TextView? = null
        var tvCond: TextView? = null

        init {
            tvDay = view.findViewById(R.id.tvDay)
            imvIcon = view.findViewById(R.id.imvIcon)
            tvMin = view.findViewById(R.id.tvMin)
            tvMax = view.findViewById(R.id.tvMax)
            tvCond = view.findViewById(R.id.tvCond)
        }
    }

