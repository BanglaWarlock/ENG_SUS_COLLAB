package com.example.eng_sus_collab

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.semantics.text
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class AdminAnalytics : AppCompatActivity(){

    private var station_to_delete = ""
    private var station_to_edit = ""
    private var edited_station_name = ""

    private var to_delete = false
    private var to_edit = false


    companion object
    {

        const val ADMIN_ANALYTICS_REQUEST_CODE = 201 // Unique request code for this fragment

        // Define keys for data you expect back, consistent with AdminAnalytics
        const val ACTION_TYPE_KEY = "action_type"
        const val ACTION_DELETE = "action_delete"
        const val ACTION_EDIT = "action_edit"
        const val EDITED_STATION_NAME = "edited_station_name" // Ex
        const val EDITED_STATION = "edited_station" // Ex
        const val DELETED_STATION_NAME_KEY = "deleted_station_name" // Ex
    }

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
        setupDeleteButton()
        setupEditButton()

        setupLayout()
    }

    private fun setupEditButton()
    {
        var editButton = findViewById<Button>(R.id.editbtn)

        editButton.setOnClickListener {
            to_edit = true
            station_to_edit = intent.getStringExtra("station_name")!!
            edited_station_name = "EDIT#" + Random.nextInt(100000,900000).toString()
            prepareAndFinishWithData()
        }
    }

    private fun setupDeleteButton()
    {
        var deleteButton = findViewById<Button>(R.id.delbtn)



        deleteButton.setOnClickListener {
            to_delete = true
            station_to_delete = intent.getStringExtra("station_name")!!
            prepareAndFinishWithData()
            super.finish()

        }
    }

    private fun setupBackButton()
    {
        var backButton = findViewById<TextView>(R.id.back_butt_admin)
        backButton.setOnClickListener {
            super.finish()
        }
    }

    private fun prepareAndFinishWithData()
    {
        val resultIntent = Intent()

        if (to_delete) {
            resultIntent.putExtra(ACTION_TYPE_KEY, ACTION_DELETE)
            resultIntent.putExtra(DELETED_STATION_NAME_KEY, station_to_delete)
        } else if (to_edit) {
            resultIntent.putExtra(ACTION_TYPE_KEY, ACTION_EDIT)
                resultIntent.putExtra(EDITED_STATION, intent.getStringExtra("station_name")!!)
            resultIntent.putExtra(EDITED_STATION_NAME, edited_station_name)
        }

        setResult(Activity.RESULT_OK, resultIntent)
        super.finish()

    }

    override fun onBackPressed() {
        super.onBackPressed()

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