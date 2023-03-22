package com.antique.farewell.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import com.antique.common.util.EventObserver
import com.antique.common.util.LoginState
import com.antique.farewell.R
import com.antique.farewell.databinding.ActivityCoordinateBinding

class CoordinateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCoordinateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_coordinate)

        initialize()
    }

    private fun initialize() {
        setUpEdgeStyle()
        setupObservers()
    }

    private fun setUpEdgeStyle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowCompat.setDecorFitsSystemWindows(window, false)
        } else {
            binding.root.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }
    }

    private fun setupObservers() {
        LoginState.isLogin.observe(this, EventObserver {
            if(it) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })
    }
}