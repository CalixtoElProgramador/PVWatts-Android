package com.example.pvwatts.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.navigation.ui.setupWithNavController
import com.example.pvwatts.R
import com.example.pvwatts.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment
        val navController = navHostFragment.navController

        binding.lottieFavorites.setOnClickListener {
            Toast.makeText(this, "Favorites", Toast.LENGTH_SHORT).show()
        }

        binding.bottomNavigationBar.setupWithNavController(navController)
        observeDestinationChange(navController)

    }

    private fun observeDestinationChange(navController: NavController) {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.eFABAddProject.setOnClickListener {
                when (destination.id) {
                    R.id.homeFragment -> {
                        findNavController(R.id.nav_host_main).navigate(R.id.action_homeFragment_to_addProjectFragment00)
                    }
                    R.id.exploreFragment -> {
                        findNavController(R.id.nav_host_main).navigate(R.id.action_exploreFragment_to_addProjectFragment03)
                    }
                }
            }

            when (destination.id) {
                R.id.addProjectFragment00 -> {
                    binding.bottomNavigationBar.visibility = View.GONE
                    binding.eFABAddProject.visibility = View.GONE
                    binding.buttonsCreateProject.visibility = View.VISIBLE
                    binding.lottieFavorites.visibility = View.GONE
                    binding.lottieMore.visibility = View.GONE
                    binding.lottieSearch.visibility = View.GONE
                }
                R.id.addProjectFragment01 -> {
                }
                R.id.addProjectFragment02 -> {
                }
                R.id.addProjectFragment03 -> {
                }
                R.id.addProjectFragment04 -> {
                }
                R.id.addProjectFragment05 -> {
                }
                R.id.addProjectFragment06 -> {
                }

                else -> {
                    binding.bottomNavigationBar.visibility = View.VISIBLE
                    binding.eFABAddProject.visibility = View.VISIBLE
                    binding.buttonsCreateProject.visibility = View.GONE
                    binding.lottieFavorites.visibility = View.VISIBLE
                    binding.lottieMore.visibility = View.VISIBLE
                    binding.lottieSearch.visibility = View.VISIBLE
                }
            }
        }
    }

}