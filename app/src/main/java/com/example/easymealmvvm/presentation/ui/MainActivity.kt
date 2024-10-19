package com.example.easymealmvvm.presentation.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.easymealmvvm.R
import com.example.easymealmvvm.data.local.MealDatabase
import com.example.easymealmvvm.databinding.ActivityMainBinding
import com.example.easymealmvvm.presentation.view_model.MainViewModel
import com.example.easymealmvvm.presentation.view_model.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

      val mainViewModel by lazy {
         val db = MealDatabase.getInstance(this)
         val viewModelFactory = MainViewModelFactory(db)
         ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
     }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

       binding.bottomNavigation.setupWithNavController(findNavController(R.id.navHostFragment))


    }
}