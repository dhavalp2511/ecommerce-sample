package com.example.ecommercedemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ecommercedemo.R
import com.example.ecommercedemo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.databinding.DataBindingUtil.setContentView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= setContentView(this, R.layout.activity_main)
    }
}