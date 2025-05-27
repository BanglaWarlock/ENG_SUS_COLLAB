package com.example.eng_sus_collab

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminView.newInstance] factory method to
 * create an instance of this fragment.
 */

interface NearbyStationClickListenerAdmin {
    fun onStationClickAdmin(stationItem: NearbyStationItem)
}


class AdminView : Fragment() {
    // TODO: Rename and change types of parameters
    private var is_admin: Boolean? = null
    private var param2: String? = null

    private var stationList = ArrayList<NearbyStationItem>()
    private  lateinit var  stationListAdapter: AdminStationAdapter
    private lateinit var listener: NearbyStationClickListenerAdmin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            is_admin = it.getBoolean(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun setupStationLists()
    {
        if (stationList.size > 0)
        {
            stationListAdapter.notifyDataSetChanged()
            return
        }

        stationList.add(NearbyStationItem("Rembus", 2.0))
        stationList.add(NearbyStationItem("Universiti Station", 1.85))
        stationList.add(NearbyStationItem("Melaban", 0.57))
        stationList.add(NearbyStationItem("Sigitin", 3.21))
        stationList.add(NearbyStationItem("Unimas", 2.44))
        stationList.add(NearbyStationItem("Heart Centre", 0.92))
        stationList.add(NearbyStationItem("Riveria", 4.76))
        stationList.add(NearbyStationItem("Stutong", 1.39))
        stationList.add(NearbyStationItem("Wan Alwi", 2.88))
        stationList.add(NearbyStationItem("Viva City Mall", 0.63))
        stationList.add(NearbyStationItem("Simpang Tiga", 3.07))
        stationList.add(NearbyStationItem("The Spring", 1.02))
        stationList.add(NearbyStationItem("Batu Lintang", 2.12))
        stationList.add(NearbyStationItem("Sarawak Hospital", 0.45))
        stationList.add(NearbyStationItem("Hikmah Exchange", 3.90))
        stationList.add(NearbyStationItem("Aeon Mall", 4.04))
        stationList.add(NearbyStationItem("Kuching Sentral", 2.77))
        stationList.add(NearbyStationItem("Kuching Airport", 0.33))
        stationList.add(NearbyStationItem("Pelita Height", 1.96))
        stationList.add(NearbyStationItem("Tun Jugah", 4.59))
        stationList.add(NearbyStationItem("Jalan Tun Razak", 2.23))

        stationList.sortBy { it.station_distance }

        stationListAdapter.setLists(stationList)
        stationListAdapter.notifyDataSetChanged()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_admin_view, container, false)

        // setup recycler view

        // set up recycler view
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerAdmin)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        stationListAdapter = AdminStationAdapter(stationList, this)
        recyclerView.adapter = stationListAdapter

        listener = context as NearbyStationClickListenerAdmin

        setupStationLists()
        return view
    }

    fun onStationClick(stationItem: NearbyStationItem)
    {
//        listener.onStationClickAdmin(stationItem)
        val intent = Intent(requireActivity(), AdminAnalytics::class.java)
        // Optionally, pass some initial data to AdminAnalytics if needed
        intent.putExtra("station_name", stationItem.station_name)
        startActivityForResult(intent, ADMIN_ANALYTICS_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data) // Important to call super

        if (requestCode == ADMIN_ANALYTICS_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                when (data.getStringExtra(ACTION_TYPE_KEY)) {
                    ACTION_DELETE -> {
                        val deleted_station_name = data.getStringExtra(DELETED_STATION_NAME_KEY)
                        stationList.removeIf { it.station_name == deleted_station_name }

                        stationListAdapter.notifyDataSetChanged()
                    }
                    ACTION_EDIT -> {
                        val editedStationName = data.getStringExtra(EDITED_STATION)
                        val stationToEdit = stationList.find { it.station_name == editedStationName }
                        if (stationToEdit != null) {
                            stationToEdit.station_name = data.getStringExtra(EDITED_STATION_NAME)!!
                            stationListAdapter.notifyDataSetChanged()
                        }
                    }
                    else -> {
                        Log.d("YourFragment", "AdminAnalytics returned with unknown action or general data.")
                        // Handle other general data if necessary
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.d("YourFragment", "AdminAnalytics was cancelled or returned no specific data.")
                // Handle cancellation if needed
            }
        }
    }

    companion object {

        const val ADMIN_ANALYTICS_REQUEST_CODE = 201 // Unique request code for this fragment
        // Define keys for data you expect back, consistent with AdminAnalytics
        const val ACTION_TYPE_KEY = "action_type"
        const val ACTION_DELETE = "action_delete"
        const val ACTION_EDIT = "action_edit"
        const val EDITED_STATION_NAME = "edited_station_name" // Ex
        const val EDITED_STATION = "edited_station" // Ex
        const val DELETED_STATION_NAME_KEY = "deleted_station_name" // Ex
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdminView.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Boolean, param2: String) =
            AdminView().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}