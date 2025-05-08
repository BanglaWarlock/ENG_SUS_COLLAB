package com.example.eng_sus_collab

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eng_sus_collab.databinding.ActivityFullDetailBinding
import kotlin.random.Random

class FullDetail : AppCompatActivity() {

    private var percent_list = ArrayList<ArrayList<Int>>()
    private  lateinit var  percentListAdapter: PercentListAdapter

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_full_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.full_detail)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupBackButton()
        setupLayout()
        // Populate percent_list with some sample data
        // Replace this with your actual data loading logic
        populatePercentList()

        setupRecyclerView()
    }

    // Function to populate percent_list with sample data
    private fun populatePercentList() {

        var percent = ArrayList<Int>()
        for (i in 0..15) {
            percent.add( intent.getIntExtra("battery_percent$i", 0))
        }

        percent_list.add(percent)

        percent = ArrayList<Int>()
        for (i in 16..31) {
            percent.add( intent.getIntExtra("battery_percent$i", 0))
        }
        percent_list.add(percent)

        percent = ArrayList<Int>()
        for (i in 32..47) {
            percent.add( intent.getIntExtra("battery_percent$i", 0))
        }
        percent_list.add(percent)

        percent = ArrayList<Int>()
        for (i in 48..63) {
            percent.add( intent.getIntExtra("battery_percent$i", 0))
        }
        percent_list.add(percent)
    }


    private fun setupRecyclerView()
    {
        // set up recycler view
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_full_detail) // Use findViewById directly
        recyclerView.layoutManager = LinearLayoutManager(this) // Use 'this' for Activity context

        // Initialize the adapter with your data
        percentListAdapter = PercentListAdapter(percent_list) // Assuming your adapter takes a List<ArrayList<Int>>
        recyclerView.adapter = percentListAdapter

        // If you update the data later, remember to call:
        // percentListAdapter.notifyDataSetChanged()
    }

    private fun setupLayout()
    {
        var station_name = intent.getStringExtra("station_name")
        findViewById<TextView>(R.id.full_detail_station_name).text = station_name

        var station_distance = intent.getDoubleExtra("station_distance", 0.0)
        findViewById<TextView>(R.id.full_detail_station_distance).text = "Distance : $station_distance km"
    }

    private fun setupBackButton()
    {
        var backButton = findViewById<TextView>(R.id.back_butt_full_detail)
        backButton.setOnClickListener {
            finish()
        }

    }
}