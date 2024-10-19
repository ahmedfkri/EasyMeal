package com.example.easymealmvvm.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easymealmvvm.data.model.Category
import com.example.easymealmvvm.data.model.PopularMeal
import com.example.easymealmvvm.databinding.CategoryListItemBinding

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    var onItemClick: ((Category) -> Unit)? = null


    //DiffUtil
    val diffUtil = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffUtil)

    class CategoryViewHolder(val binding: CategoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            CategoryListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = differ.currentList[position]
        holder.binding.txtCategoryName.text = category.strCategory
        Glide.with(holder.itemView).load(category.strCategoryThumb)
            .into(holder.binding.imgCategory)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(category)
        }
    }
}