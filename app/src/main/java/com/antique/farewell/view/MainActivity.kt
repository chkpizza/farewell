package com.antique.farewell.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.antique.farewell.R
import com.antique.farewell.databinding.ActivityMainBinding
import com.antique.settings.presentation.OnSignOutListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), OnSignOutListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initialize()
    }

    private fun initialize() {
        setupEdgeScreen()
        setupNavigation()
    }

    private fun setupEdgeScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowCompat.setDecorFitsSystemWindows(window, false)
        } else {
            binding.root.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navigation_host_view) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                com.antique.story.R.id.storyFragment -> binding.bottomNavigationView.isVisible = true
                com.antique.information.R.id.informationFragment -> binding.bottomNavigationView.isVisible = true
                com.antique.settings.R.id.settingsFragment -> binding.bottomNavigationView.isVisible = true
                else -> binding.bottomNavigationView.isVisible = false
            }
        }
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    override fun signOut() {
        Firebase.auth.signOut()
        startActivity(Intent(this, CoordinateActivity::class.java))
        finish()
    }
}