package com.example.easymealmvvm.presentation.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.easymealmvvm.R
import com.example.easymealmvvm.core.Constants.MEAL_ID
import com.example.easymealmvvm.core.Constants.MEAL_NAME
import com.example.easymealmvvm.core.Constants.MEAL_THUMB
import com.example.easymealmvvm.data.local.MealDatabase
import com.example.easymealmvvm.data.model.Meal
import com.example.easymealmvvm.databinding.ActivityMealBinding
import com.example.easymealmvvm.presentation.view_model.MealViewModel
import com.example.easymealmvvm.presentation.view_model.MealViewModelFactory

class MealActivity : AppCompatActivity() {

    lateinit var binding: ActivityMealBinding
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var mealYouTubeLink: String
    private lateinit var viewModel: MealViewModel

    lateinit var meal: Meal


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMealBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val db = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(db)
        viewModel = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getMealInformationFromIntent()
        showMealData()
        loadingCase()
        viewModel.getMealDetails(mealId)
        observeMealDetailsLiveData()

        onFavoriteClick()
        onYoutubeClick()

    }

    private fun onFavoriteClick() {
        binding.fabFav.setOnClickListener {
            meal.let {
                viewModel.insertMeal(it)
                Toast.makeText(this, "Inserted ${it.strMeal}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mealYouTubeLink))
            startActivity(intent)
        }
    }

    private fun observeMealDetailsLiveData() {
        viewModel.mealDetailsLiveData.observe(this
        ) { value ->
            onResponseCase()
            meal = value
            binding.txtContent.text = meal.strInstructions
            binding.txtCat.text = "Category : ${meal.strCategory}"
            binding.txtArea.text = "Area : ${meal.strArea}"

            mealYouTubeLink= meal.strYoutube!!
        }
    }

    private fun showMealData() {
        Glide.with(applicationContext).load(mealThumb).into(binding.imgMealDetails)
        binding.collapsingToolbar.title = mealName
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(MEAL_ID)!!
        mealName = intent.getStringExtra(MEAL_NAME)!!
        mealThumb = intent.getStringExtra(MEAL_THUMB)!!
    }

    private fun loadingCase(){
        binding.apply {
            progressBar.visibility= View.VISIBLE
            fabFav.visibility= View.INVISIBLE
            txtInstructions.visibility= View.INVISIBLE
            txtArea.visibility= View.INVISIBLE
            txtCat.visibility= View.INVISIBLE
            imgYoutube.visibility= View.INVISIBLE

        }

    }

    private fun onResponseCase(){
        binding.apply {
            progressBar.visibility= View.INVISIBLE
            fabFav.visibility= View.VISIBLE
            txtInstructions.visibility= View.VISIBLE
            txtArea.visibility= View.VISIBLE
            txtCat.visibility= View.VISIBLE
            imgYoutube.visibility= View.VISIBLE
        }
    }
}