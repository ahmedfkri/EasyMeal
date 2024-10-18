package com.example.easymealmvvm.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easymealmvvm.data.model.Category
import com.example.easymealmvvm.data.model.PopularMeal
import com.example.easymealmvvm.databinding.CategoryListItemBinding

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private var categoriesList = ArrayList<Category>()

    var onItemClick: ((Category) -> Unit)? = null

    fun setCategoryList(categoryList: List<Category>) {
        this.categoriesList = categoryList as ArrayList<Category>
        notifyDataSetChanged()
    }

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

    override fun getItemCount() = categoriesList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoriesList[position]
        holder.binding.txtCategoryName.text = category.strCategory
        Glide.with(holder.itemView).load(category.strCategoryThumb)
            .into(holder.binding.imgCategory)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(category)
        }
    }
}