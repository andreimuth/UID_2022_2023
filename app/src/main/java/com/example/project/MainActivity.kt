package com.example.project

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()
        initListeners()
    }

    private fun initBottomNavigation() {
        val navHost: NavHostFragment = binding.navHostFragment.getFragment()
        navController = navHost.findNavController()

        val bottomNavView = binding.bottomNavBar
        //take
        bottomNavView.setupWithNavController(navController)
    }

    private fun initListeners() {
        navController.addOnDestinationChangedListener(this)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        if(destination.id == R.id.login || destination.id == R.id.register) {
            binding.bottomNavBar.visibility = View.GONE
        } else {
            binding.bottomNavBar.visibility = View.VISIBLE
        }
    }
}