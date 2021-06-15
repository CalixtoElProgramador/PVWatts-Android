package com.example.pvwatts.ui.pvwatts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import com.example.pvwatts.R
import com.example.pvwatts.databinding.ActivityPvwattsBinding

class PVWattsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPvwattsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPvwattsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController

        observeDestinationChange(navController)

    }

    private fun observeDestinationChange(navController: NavController) {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.outputsFragment -> {
                    binding.buttonBack.visibility = View.GONE
                    binding.buttonNext.visibility = View.GONE
                }

                else -> {
                    binding.buttonBack.visibility = View.VISIBLE
                    binding.buttonNext.visibility = View.VISIBLE
                }
            }
        }
    }

}