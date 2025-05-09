package com.example.eng_sus_collab

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.semantics.text
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class AdminAnalytics : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_analytics)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.admin_analytics)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupBackButton()

        setupLayout()
    }

    private fun setupBackButton()
    {
        var backButton = findViewById<TextView>(R.id.back_butt_admin)
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun setupLayout()
    {
        // station name
        findViewById<TextView>(R.id.admin_station_name).text = intent.getStringExtra("station_name")

        var today_output = Random.nextDouble(20.0, 945.39)
        // todays output
        findViewById<TextView>(R.id.today_output).text =
            "Today's Output: %.2fkWh".format(today_output)


        val monthlyOutput = today_output + Random.nextDouble(0.0, 945.39)
        findViewById<TextView>(R.id.monthly_output).text =
            "Monthly Output: %.2fkWh".format(monthlyOutput)
        // peak usage
        findViewById<TextView>(R.id.peak_usage).text = "Peak Usage: ${Random.nextInt(0,24)}:${Random.nextInt(0,6)}${Random.nextInt(0,10)}hrs"

        // active ports
        findViewById<TextView>(R.id.active_ports).text = "Available Ports: ${Random.nextInt(1, 16)} / 16"

    }
}