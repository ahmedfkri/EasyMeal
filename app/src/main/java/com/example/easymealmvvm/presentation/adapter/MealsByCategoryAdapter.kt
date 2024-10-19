package com.example.easymealmvvm.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easymealmvvm.data.model.PopularMeal
import com.example.easymealmvvm.databinding.MealListItemBinding

class MealsByCategoryAdapter : RecyclerView.Adapter<MealsByCategoryAdapter.CategoryViewHolder>() {


    //DiffUtil
    val diffUtil = object : DiffUtil.ItemCallback<PopularMeal>(){
        override fun areItemsTheSame(oldItem: PopularMeal, newItem: PopularMeal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: PopularMeal, newItem: PopularMeal): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffUtil)

    class CategoryViewHolder(val binding: MealListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            MealListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val meal = differ.currentList[position]
        holder.binding.txtMealName.text = meal.strMeal
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.imgMeal)

    }


}