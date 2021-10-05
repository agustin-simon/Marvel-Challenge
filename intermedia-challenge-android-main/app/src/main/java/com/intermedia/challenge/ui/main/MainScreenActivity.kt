package com.intermedia.challenge.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.intermedia.challenge.R
import com.intermedia.challenge.databinding.ActivityMainScreenBinding
import com.intermedia.challenge.ui.login.LoginActivity

class MainScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainScreenBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment)
        binding.bottomNavView.setupWithNavController(navController)
        binding.bottomNavView.itemIconTintList = null
        binding.iconLogOut.setOnClickListener {
            setupLogOut()
        }
    }

    private fun setupLogOut() {
        val auth = Firebase.auth
        auth.signOut()
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        sharedPref.edit().clear().apply()
        startActivity(Intent(this, LoginActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        })
        finish()
    }

}