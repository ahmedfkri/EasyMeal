package com.example.easymealmvvm.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easymealmvvm.data.model.Meal
import com.example.easymealmvvm.databinding.MealListItemBinding

class FavoriteMealsAdapter : RecyclerView.Adapter<FavoriteMealsAdapter.FavoriteMealViewHolder>() {

    lateinit var onItemClick: ((Meal) -> Unit)


    //DiffUtil
    private val diffUtil = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
           return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }

    }
    val differ= AsyncListDiffer(this,diffUtil)




    class FavoriteMealViewHolder(val binding: MealListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteMealsAdapter.FavoriteMealViewHolder {
        return  FavoriteMealViewHolder(
            MealListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: FavoriteMealsAdapter.FavoriteMealViewHolder,
        position: Int
    ) {
        val meal = differ.currentList[position]
        holder.binding.apply {
            txtMealName.text = meal.strMeal
            Glide.with(holder.itemView)
                .load(meal.strMealThumb)
                .into(imgMeal)
        }
        holder.itemView.setOnClickListener {
            onItemClick.invoke(meal)
        }
    }

    override fun getItemCount()  =differ.currentList.size
}