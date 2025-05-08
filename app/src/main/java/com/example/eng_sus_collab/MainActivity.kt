package com.example.eng_sus_collab

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), NearbyStationClickListener, NearbyStationClickListenerAdmin, onAdminClicks
{
    private var activeFragment: Fragment? = null
    private var is_admin = false

    private var nearMeFragment = NearMe()
    private var mapFragment = MapView()
    private var adminFragment = AdminView()
    private var settingsFragment = SettingsView()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupFragments()
        setupBottomNavigation("near")
    }

    private fun setupBottomNavigation(tab:String = "")
    {
        // Set up bottom navigation
        // get bottom nav
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        // clear menu so you dont run into max item error
        bottomNav.menu.clear()

        // which kind of menu to display
        if (is_admin) {
            bottomNav.inflateMenu(R.menu.nav_menu)
        } else {
            bottomNav.inflateMenu(R.menu.nav_menu_normal)
        }

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.near -> {
                    showFragment(nearMeFragment)
                    true
                }
                R.id.map -> {
                    showFragment(mapFragment)
                    true
                }
                R.id.admin -> {
                    showFragment(adminFragment)
                    true
                }
                R.id.settings -> {
                    showFragment(settingsFragment)
                    true
                }
                else -> false
            }
        }

        // if called from setting page don't change selected
        if (tab == "settings") {
            bottomNav.selectedItemId = R.id.settings
            return
        }

        // Set default selected item
        bottomNav.selectedItemId = R.id.near
    }

    private fun setupFragments() {
        // Initialize all fragments


        // Start with all fragments added but only NearMe visible
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView2, nearMeFragment)
        transaction.commit()

        // Set the active fragment
        activeFragment = nearMeFragment
    }

    private fun showFragment(fragment: Fragment) {

        if (fragment is SettingsView) {
            settingsFragment = SettingsView.newInstance(is_admin, "")
            // replace fragment otherwise
            activeFragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView2, settingsFragment)
                    .commit()
            }
            activeFragment = settingsFragment
            return
        }


        // Don't do anything if the fragment is already showing
        if (fragment === activeFragment) return





            // replace fragment otherwise
            activeFragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView2, fragment)
                    .commit()
            }


        // Update the active fragment reference
        activeFragment = fragment
    }

    override fun onStationClick(stationItem: NearbyStationItem)
    {
        // Create an Intent to start StationDetailActivity
        val intent = Intent(this, FullDetail::class.java)

        intent.putExtra("station_name", stationItem.station_name)
        intent.putExtra("station_distance", stationItem.station_distance)

        for (i in 0..63) {
            intent.putExtra("battery_percent$i", stationItem.battery_percents[i])
        }

        // Start the new Activity
        startActivity(intent)
    }

    override fun onBecomeAdmin()
    {
        if (is_admin) { return }

        is_admin = true
        setupBottomNavigation("settings")
        showFragment(settingsFragment)
    }

    override fun onLogout()
    {
        if (!is_admin) { return }

        is_admin = false
        setupBottomNavigation("settings")
        showFragment(settingsFragment)
    }

    override fun onSupport()
    {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Support Requested")
        builder.setMessage("Support no longer available!")

        builder.setPositiveButton("OK") { dialog, which ->
            // Action on OK button click
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            // Action on Cancel button click
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onStationClickAdmin(stationItem: NearbyStationItem)
    {
//        // Create an Intent to start StationDetailActivity
//        val intent = Intent(this, AdminAnalytics::class.java)
//
//        intent.putExtra("station_name", stationItem.station_name)
//        intent.putExtra("station_distance", stationItem.station_distance)
//
//        for (i in 0..63) {
//            intent.putExtra("battery_percent$i", stationItem.battery_percents[i])
//        }
//
//        // Start the new Activity
//        startActivity(intent)
    }
}