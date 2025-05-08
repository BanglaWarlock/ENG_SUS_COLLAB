package com.example.eng_sus_collab

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class PercentListAdapter (private var percentList: ArrayList<ArrayList<Int>>) :
    RecyclerView.Adapter<PercentListAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PercentListAdapter.ViewHolder
    {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.station_slots_detail, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PercentListAdapter.ViewHolder, position: Int)
    {
        val percents = percentList[position]

        var battery_left = 0

        var slot_id = ArrayList<Int>()
        slot_id.add(R.id.textView2)
        slot_id.add(R.id.textView3)
        slot_id.add(R.id.textView4)
        slot_id.add(R.id.textView5)
        slot_id.add(R.id.textView6)
        slot_id.add(R.id.textView7)
        slot_id.add(R.id.textView8)
        slot_id.add(R.id.textView9)
        slot_id.add(R.id.textView10)
        slot_id.add(R.id.textView11)
        slot_id.add(R.id.textView12)
        slot_id.add(R.id.textView13)
        slot_id.add(R.id.textView14)
        slot_id.add(R.id.textView15)
        slot_id.add(R.id.textView16)
        slot_id.add(R.id.textView17)

        for (i in 0..15) {
            var curr_percent = percents[i]
            var textView = holder.itemView.findViewById<TextView>(slot_id[i])

            if (curr_percent <= 40) {
                textView.setBackgroundColor(Color.RED)
            } else if (curr_percent <= 90) {
                textView.setBackgroundColor(Color.YELLOW)
            }else {
                textView.setBackgroundColor(Color.GREEN)
                battery_left += 1
            }

            textView.text = curr_percent.toString() + "%"

        }

        holder.itemView.findViewById<TextView>(R.id.battery_left).text = "Battery Left: " + battery_left.toString() + "/18"


//
    }

    override fun getItemCount(): Int
    {
        return percentList.size
    }

    fun setLists(percents: ArrayList<ArrayList<Int>>)
    {
        percentList = percents
    }
}