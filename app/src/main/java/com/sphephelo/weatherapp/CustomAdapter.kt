package com.sphephelo.weather.ui

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sphephelo.weatherapp.R


class CustomAdapter(val maxTemp:ArrayList<Int>,val weekDays:ArrayList<String>,val icons:ArrayList<Int>) :
        RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val weekday: TextView
        val icon:ImageView
        val temp:TextView


        init {
            // Define click listener for the ViewHolder's View.
            weekday = view.findViewById(R.id.weekday)
            icon=view.findViewById(R.id.weather_icon)
            temp=view.findViewById(R.id.temperature)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.recycle_layout, viewGroup, false)
//Code by Sphephelo Mabena§
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        //viewHolder.textView.text = dataSet[position]

        viewHolder.weekday.text = weekDays[position];
        viewHolder.temp.text="${maxTemp[position].toString()} \u2103";
        println("Icons $icons")
        viewHolder.icon.setBackgroundResource(icons[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    //override fun getItemCount() = dataSet.size
    override fun getItemCount() = maxTemp.size

}