package com.example.anchorbooks_rodrigojaviergarridodagle.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.anchorbooks_rodrigojaviergarridodagle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}