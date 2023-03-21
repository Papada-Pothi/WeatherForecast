package com.example.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

class Adapter(
    val time: ArrayList<String?>,
    val temp: ArrayList<Double?>,
    val cond: ArrayList<String?>,
    val image: ArrayList<Int?>
) : RecyclerView.Adapter<hourlyViewHolder>()
{
    override fun getItemCount(): Int =  time.size

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): hourlyViewHolder {
        val view = hourlyViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.hourlyitem, p0, false))
        return view
    }

    override fun onBindViewHolder(holder: hourlyViewHolder, position: Int) {
        holder.timeHour?.text = time.get(position)
        holder.tempHour?.text = temp.get(position).toString()+"°"
        holder.desHour?.text = cond.get(position).toString()
        val img =image.get(position)
        holder.imvHour?.setImageResource(img!!)
    }
}

class hourlyViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    var timeHour: TextView? = null
    var imvHour: ImageView? = null
    var tempHour: TextView? = null
    var desHour: TextView? = null

    init {
        timeHour = view.findViewById(R.id.tvTimeHour)
        imvHour = view.findViewById(R.id.imvHour)
        tempHour = view.findViewById(R.id.tvTempHour)
        desHour = view.findViewById(R.id.tvDesHour)
    }
}