package com.antique.farewell.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.antique.common.data.User
import com.antique.common.util.Constant
import com.antique.farewell.R
import com.antique.farewell.databinding.ActivityMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.signOutButton.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this, CoordinateActivity::class.java))
            finish()
        }

        Firebase.database.reference.child(Constant.USER_NODE).child(Firebase.auth.currentUser?.uid.toString()).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue(User::class.java)?.let {
                    binding.testView.text = it.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}