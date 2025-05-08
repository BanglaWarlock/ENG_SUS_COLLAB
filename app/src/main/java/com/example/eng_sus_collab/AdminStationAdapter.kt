package com.example.eng_sus_collab

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.eng_sus_collab.NearbyStationAdapter.ViewHolder

class AdminStationAdapter(private var stationList : ArrayList<NearbyStationItem>, private var clickListener: AdminView) :
    RecyclerView.Adapter<AdminStationAdapter.ViewHolder>()
{
    private lateinit var context: Context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val stationName = itemView.findViewById<TextView>(R.id.text_StName2)
        val stationDistance = itemView.findViewById<TextView>(R.id.text_Date)
        val stationContainer = itemView.findViewById<CardView>(R.id.stationContainer)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdminStationAdapter.ViewHolder
    {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.station_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdminStationAdapter.ViewHolder, position: Int)
    {
        val stationItem = stationList[position]

        // name and due date is always there
        holder.stationName.text = stationItem.station_name
        holder.stationDistance.text = stationItem.station_distance.toString() + " km"


        // set event listeners ?? TO DO IT LATER

        holder.stationContainer.setOnClickListener {
            clickListener.onStationClick(stationItem)
        }
    }

    override fun getItemCount(): Int
    {
        return stationList.size
    }

    fun setLists(stations: ArrayList<NearbyStationItem>)
    {
        stationList = stations
    }
}