package com.example.eng_sus_collab

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.random.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MapView.newInstance] factory method to
 * create an instance of this fragment.
 */
interface  infoCardClickListener
{
    fun onInfoCardClick(stationItem: NearbyStationItem)
}

class MapView : Fragment(), OnMapReadyCallback,  GoogleMap.OnMarkerClickListener
{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var selecting = false

    private lateinit var mapView: com.google.android.gms.maps.MapView // where the map is on my screen
    private lateinit var fusedLocationClient: FusedLocationProviderClient // for getting my latlng
    private lateinit var googleMap: GoogleMap // gMap instance
    private lateinit var latitude : String
    private  lateinit var longitude : String

    private lateinit var info_card : CardView

    private var zoom_scale = 13f

    private lateinit var listener: infoCardClickListener


    // Map to associate markers with NearbyStationItem objects
    private val markerStationMap = mutableMapOf<Marker, NearbyStationItem>()

    var stations = ArrayList<NearbyStationItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_map_view, container, false)

        listener = context as infoCardClickListener


        mapView = view.findViewById(R.id.mapView)
        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        var truth = isLocationPermissionGranted()
        getLastLocation()

        info_card = view.findViewById<CardView>(R.id.map_info)
        info_card.visibility = View.GONE


        val mapViewBundle = savedInstanceState?.getBundle(MAP_VIEW_BUNDLE_KEY)
        mapView.onCreate(mapViewBundle)
        mapView.getMapAsync(this)

        stations.clear()

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
        stations.add(NearbyStationItem("Sarawak Hospital", 0.45))
        stations.add(NearbyStationItem("Hikmah Exchange", 3.90))
        stations.add(NearbyStationItem("Aeon Mall", 4.04))
        stations.add(NearbyStationItem("Kuching Sentral", 2.77))
        stations.add(NearbyStationItem("Kuching Airport", 0.33))
        stations.add(NearbyStationItem("Pelita Height", 1.96))
        stations.add(NearbyStationItem("Tun Jugah", 4.59))
        stations.add(NearbyStationItem("Jalan Tun Razak", 2.23))

        stations.sortBy { it.station_distance }

        return view
    }

    // returns the location class, of this mobile
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                )
                {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        latitude = "${location.latitude}"
                        longitude = "${location.longitude}"
                        if (::googleMap.isInitialized) {

                            // currLocation = LatLng(location.latitude, location.longitude)
                            // Add your marker or perform other operations with the location

                            // Add your marker here
//                            val markerOptions = MarkerOptions()
//                                .position(LatLng(latitude.toDouble(), longitude.toDouble()))
//                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
//                            val marker = googleMap.addMarker(markerOptions)
//
//                            markerStationMap[marker!!] = stations[0]


                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude.toDouble(), longitude.toDouble()), zoom_scale))

                            setupMarkers()

                        }

                    } else {
                        // Handle the case where the location is null
                        // You can try requesting location updates or show an error message
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please enable location services", Toast.LENGTH_SHORT).show()
            }
        } else {
            requestPermissions()
        }
    }




    private fun setupMarkers()
    {

        // clear all markers
        googleMap.clear()
        markerStationMap.clear()

        // Re-add current location marker (station[0]) if needed
        val currentLatLng = LatLng(latitude.toDouble(), longitude.toDouble())
        val currentMarker = googleMap.addMarker(
            MarkerOptions()
                .position(currentLatLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
        )
        if (currentMarker != null) {
            markerStationMap[currentMarker] = stations[0]
        }

        // for every item in the station list

        for (i in 1..<stations.size) {
            var multiplier = 0.001
            var mult_lat = 1
            var mult_long = 1

            if (Random.nextDouble(0.0, 1.0) > 0.5) {
                mult_lat = -1
            }

            if (Random.nextDouble(0.0, 1.0) > 0.5) {
                mult_long = -1
            }

            var final_lat = latitude.toDouble() + ((i * mult_lat) * multiplier)
            var final_long = longitude.toDouble() + ((i * mult_long) * multiplier)

            val markerOptions = MarkerOptions()
                .position(LatLng(final_lat, final_long))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
            val marker = googleMap.addMarker(markerOptions)

            markerStationMap[marker!!] = stations[i]
        }
    }

    private fun isLocationPermissionGranted(): Boolean
    {
        return if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        )
        {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                101
            )
            false
        } else
        {
            true
        }
    }

    override fun onMapReady(googleMap: GoogleMap)
    {
        this.googleMap = googleMap
        this.googleMap.setOnMarkerClickListener(this)
        // ... other map setup
        Log.d("MapsDebug", "Map is ready!")

        // first marker on current position

        googleMap.setOnMapClickListener {
            info_card.visibility = View.GONE
        }

        if (::latitude.isInitialized && ::longitude.isInitialized){

            // currLocation = LatLng(location.latitude, location.longitude)
            // Add your marker or perform other operations with the location

            // Add your marker here
            val markerOptions = MarkerOptions()
                .position(LatLng(latitude.toDouble(), longitude.toDouble()))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
            val marker = googleMap.addMarker(markerOptions)

            markerStationMap[marker!!] = stations[0]

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude.toDouble(), longitude.toDouble()), zoom_scale))

            setupMarkers()
        }


        // Add your marker here
        // Example of adding a marker and associating it with a NearbyStationItem
/*        val dummyStation = NearbyStationItem("Dummy Station", "123 Main St", LatLng(-34.0, 151.0))
        addStationMarker(dummyStation)*/


    }

/*
    // Function to add a marker for a NearbyStationItem
    private fun addStationMarker(station: NearbyStationItem) {
        val markerOptions = MarkerOptions()
            .position(station.location)
            .title(station.name) // Set the title of the info window
            .snippet(station.address) // Set the snippet (additional text) of the info window
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))

        val marker = googleMap.addMarker(markerOptions)

        if (marker != null) {
            Log.d("MapsDebug", "Marker added successfully for ${station.name} at ${marker.position}")
            // Associate the marker with the station data
            markerStationMap[marker] = station
        } else {
            Log.d("MapsDebug", "Failed to add marker for ${station.name}")
        }
    }
*/

    // Implement the OnMarkerClickListener interface method
    override fun onMarkerClick(marker: Marker): Boolean
    {
        info_card.visibility = View.VISIBLE

        // Handle the marker click event here
        // You can access the clicked marker object

        // Retrieve the associated NearbyStationItem
        val clickedStation = markerStationMap[marker]

        // update card info

        info_card.findViewById<TextView>(R.id.map_st_name).text = clickedStation?.station_name.toString()
        info_card.findViewById<TextView>(R.id.map_date).text = clickedStation?.station_distance.toString() + " km"

        info_card.setOnClickListener {
            listener.onInfoCardClick(clickedStation!!)
        }

/*        clickedStation?.let {
            // Do something with the clicked station data
            // For example, display a Toast or show an InfoWindow (default behavior)
            Toast.makeText(requireContext(), "Clicked on station: ${it.station_name}", Toast.LENGTH_SHORT)
                .show()

            // You can also show the default info window
            // marker.showInfoWindow()

            // You can implement your own custom info window adapter
            // if you want more control over the info window's appearance and content.
            // See documentation for setInfoWindowAdapter on GoogleMap.

            // Return true if you have consumed the event and wish to suppress the default behavior
            // (which is to display the info window).
            // Return false if you wish to allow the default behavior.
            return false // Allows the default info window to be shown
        }*/
        return false
    }


        // done
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION_PERMISSION
        )
    }

    // done
    private fun checkPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    // done
    private fun isLocationEnabled(): Boolean {
        val locationManager = requireContext().getSystemService(android.content.Context.LOCATION_SERVICE) as android.location.LocationManager
        return locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            android.location.LocationManager.NETWORK_PROVIDER
        )
    }

/*
    //done
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }
    /// done
    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
    // done
    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
    //done
    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
    //done
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val mapViewBundle = Bundle().apply {
            mapView.onSaveInstanceState(this)
        }
        outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle)
    }
*/

    companion object {

        private const val MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey"
        private const val REQUEST_LOCATION_PERMISSION = 1
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MapView.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MapView().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}