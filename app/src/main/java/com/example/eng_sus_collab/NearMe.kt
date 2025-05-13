package com.example.eng_sus_collab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NearMe.newInstance] factory method to
 * create an instance of this fragment.
 */

interface NearbyStationClickListener {
    fun onStationClick(stationItem: NearbyStationItem)
}

class NearMe : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var stationList = ArrayList<NearbyStationItem>()
    private  lateinit var  stationListAdapter: NearbyStationAdapter
    private lateinit var listener: NearbyStationClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    private fun setupStationLists()
    {
        var stations = ArrayList<NearbyStationItem>()

        stations.add(NearbyStationItem("Rembus", 2.0))
        stations.add(NearbyStationItem("Universiti Station", 1.85))
        stations.add(NearbyStationItem("Melaban", 0.57))
        stations.add(NearbyStationItem("Sigitin", 3.21))
        stations.add(NearbyStationItem("Unimas", 2.44))
        stations.add(NearbyStationItem("Heart Centre", 0.92))
        stations.add(NearbyStationItem("Riveria", 4.76))
        stations.add(NearbyStationItem("Stutong", 1.39))
        stations.add(NearbyStationItem("Wan Alwi", 2.88))
        stations.add(NearbyStationItem("Viva City Mall", 0.63))
        stations.add(NearbyStationItem("Simpang Tiga", 3.07))
        stations.add(NearbyStationItem("The Spring", 1.02))
        stations.add(NearbyStationItem("Batu Lintang", 2.12))
        stations.add(NearbyStationItem("Sarawak General Hospital", 0.45))
        stations.add(NearbyStationItem("Hikmah Exchange", 3.90))
        stations.add(NearbyStationItem("Aeon Mall", 4.04))
        stations.add(NearbyStationItem("Kuching Sentral", 2.77))
        stations.add(NearbyStationItem("Kuching International Airport", 0.33))
        stations.add(NearbyStationItem("Pelita Height", 1.96))
        stations.add(NearbyStationItem("Tun Jugah", 4.59))
        stations.add(NearbyStationItem("Jalan Tun Razak", 2.23))

        stations.sortBy { it.station_distance }

        stationListAdapter.setLists(stations)
        stationListAdapter.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_near_me, container, false)

        // setup recycler view

        // set up recycler view
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerUser)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        stationListAdapter = NearbyStationAdapter(stationList, this)
        recyclerView.adapter = stationListAdapter

        listener = context as NearbyStationClickListener

        setupStationLists()
        return view
    }

    // This method will be called by your adapter when a station is clicked
    fun onStationClick(stationItem: NearbyStationItem) {
        listener.onStationClick(stationItem)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NearMe.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NearMe().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}