package com.example.eng_sus_collab

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity()
{
    private var activeFragment: Fragment? = null

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

        // Set up bottom navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId)
            {
                R.id.near ->
                {
                    showFragment(nearMeFragment)
                    true
                }

                R.id.map ->
                {
                    showFragment(mapFragment)
                    true
                }

                R.id.admin ->
                {
                    showFragment(adminFragment)
                    true
                }

                R.id.settings ->
                {
                    showFragment(settingsFragment)
                    true
                }

                else -> false
            }
        }

        // Set default selected item
        bottomNav.selectedItemId = R.id.near
    }

    private fun setupFragments() {
        // Initialize all fragments


        // Start with all fragments added but only NearMe visible
        val transaction = supportFragmentManager.beginTransaction()

        transaction.add(R.id.fragmentContainerView2, nearMeFragment)
        transaction.add(R.id.fragmentContainerView2, mapFragment).hide(mapFragment)
        transaction.add(R.id.fragmentContainerView2, adminFragment).hide(adminFragment)
        transaction.add(R.id.fragmentContainerView2, settingsFragment).hide(settingsFragment)

        transaction.commit()

        // Set the active fragment
        activeFragment = nearMeFragment
    }

    private fun showFragment(fragment: Fragment) {
        // Don't do anything if the fragment is already showing
        if (fragment === activeFragment) return

        // Hide the current fragment and show the new one
        activeFragment?.let {
            supportFragmentManager.beginTransaction()
                .hide(it)
                .show(fragment)
                .commit()
        }

        // Update the active fragment reference
        activeFragment = fragment
    }
}