package com.example.easymealmvvm.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easymealmvvm.data.model.PopularMeal
import com.example.easymealmvvm.databinding.PopularItemBinding

class PopularMealsAdapter : RecyclerView.Adapter<PopularMealsAdapter.PopularMealViewHolder>() {

    lateinit var onItemClick: ((PopularMeal) -> Unit)
    var onLongItemClick: ((PopularMeal) -> Unit)? = null


    //DiffUtil
    private val diffUtil = object :DiffUtil.ItemCallback<PopularMeal>(){
        override fun areItemsTheSame(oldItem: PopularMeal, newItem: PopularMeal): Boolean {

            return newItem.idMeal == oldItem.idMeal
        }

        override fun areContentsTheSame(oldItem: PopularMeal, newItem: PopularMeal): Boolean {
            return newItem == oldItem
        }

    }
    val differ = AsyncListDiffer(this,diffUtil)


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
            .load(differ.currentList[position].strMealThumb).into(holder.binding.imgFavMeal)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(differ.currentList[position])
        }

        holder.itemView.setOnLongClickListener {
            onLongItemClick?.invoke(differ.currentList[position])
            true
        }
    }

    override fun getItemCount() = differ.currentList.size

}