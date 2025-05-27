package com.example.eng_sus_collab

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.result.launch
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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
    private var shown_StationList = ArrayList<NearbyStationItem>()
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
        setupSpinner(view)
        GlobalScope.launch{

        }

        viewLifecycleOwner.lifecycleScope.launch {
            // neds to be in on create view
            // This code runs in a coroutine
            Log.d("MyFragmentCoroutine", "Coroutine started")

            var count = 1

            while (true){
                delay(1000)
                Log.d("MyFragmentCoroutine", "Coroutine running: $count")
                count++
            }

        }
        return view
    }

    private fun setupSpinner(this_view : View)
    {
        var spinner_instance = this_view.findViewById<Spinner>(R.id.near_me_spinner)
        var distanceList = mutableListOf<String>()

        distanceList.add("All")
        distanceList.add("2 km")
        distanceList.add("4 km")

        var arrayAdapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, distanceList)
        arrayAdapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item)
        spinner_instance.adapter = arrayAdapter

        // what to do when a distance is selected

        // spinner will do things when something is selected
        spinner_instance.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, i: Int, l: Long)
            {
                when (this_view.findViewById<Spinner>(R.id.near_me_spinner).selectedItem)
                {
                    "All" ->
                    {
                        shown_StationList = stationList
                        stationListAdapter.setLists(shown_StationList)
                        stationListAdapter.notifyDataSetChanged()

                    }

                    "2 km" ->
                    {
                        shown_StationList =
                            stationList.filter { it.station_distance <= 2.0 } as ArrayList<NearbyStationItem>

                        stationListAdapter.setLists(shown_StationList)
                        stationListAdapter.notifyDataSetChanged()
                    }

                    "4 km" ->
                    {
                        shown_StationList =
                            stationList.filter { it.station_distance <= 4.0 } as ArrayList<NearbyStationItem>


                        stationListAdapter.setLists(shown_StationList)
                        stationListAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?)
            {
                // do nothing
            }
        }
    }

    // This method will be called by your adapter when a station is clicked
    fun onStationClick(stationItem: NearbyStationItem) {
        listener.onStationClick(stationItem)
    }

    companion object
    {
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