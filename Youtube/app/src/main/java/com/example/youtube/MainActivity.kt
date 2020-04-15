package com.example.youtube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.mainNavHost)

        bottomNavigation.setupWithNavController(navController)
        setupActionBarWithNavController(this, navController)

        val appBarConfiguration = AppBarConfiguration(bottomNavigation.menu)
        setupActionBarWithNavController(this, navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.turkeyFragment, R.id.germanyFragment, R.id.usaFragment -> {
                    bottomNavigation.isVisible = true
                }
                else -> bottomNavigation.isVisible = false
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }
    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            R.id.videoDetailFragment ->super.onBackPressed()
            else ->  finish()
        }
    }


}