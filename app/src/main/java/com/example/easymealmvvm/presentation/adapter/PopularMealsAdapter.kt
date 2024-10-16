package com.example.easymealmvvm.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easymealmvvm.data.model.PopularMeal
import com.example.easymealmvvm.databinding.PopularItemBinding

class PopularMealsAdapter : RecyclerView.Adapter<PopularMealsAdapter.PopularMealViewHolder>() {

    lateinit var onItemClick: ((PopularMeal) -> Unit)

    private var mealsList = ArrayList<PopularMeal>()

    fun setMealList(mealList: List<PopularMeal>) {
        this.mealsList = mealList as ArrayList<PopularMeal>
        notifyDataSetChanged()
    }

    class PopularMealViewHolder(val binding: PopularItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularMealsAdapter.PopularMealViewHolder {
        return PopularMealViewHolder(
            PopularItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: PopularMealsAdapter.PopularMealViewHolder,
        position: Int
    ) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb).into(holder.binding.imgFavMeal)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])
        }
    }

    override fun getItemCount() = mealsList.size

}