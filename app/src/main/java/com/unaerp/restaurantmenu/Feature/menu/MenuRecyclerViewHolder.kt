package com.unaerp.restaurantmenu.Feature.menu

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.unaerp.restaurantmenu.databinding.ItemCategoryBinding
import com.unaerp.restaurantmenu.databinding.ItemMenuItemBinding
import com.unaerp.restaurantmenu.Domain.MenuCategory
import com.unaerp.restaurantmenu.Domain.MenuItem
import com.unaerp.restaurantmenu.R

sealed class MenuRecyclerViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class CategoryViewHolder(
        private val binding: ItemCategoryBinding
    ) : MenuRecyclerViewHolder(binding) {

        fun bind(category: MenuCategory) {
            binding.categoryTitle.text = category.title
        }
    }

    class ItemViewHolder(
        private val binding: ItemMenuItemBinding
    ) : MenuRecyclerViewHolder(binding) {

        fun bind(menuItem: MenuItem) {
            binding.itemName.text = menuItem.name
            binding.itemImage.setImageResource(R.drawable.ic_launcher_foreground)
            binding.itemPrice.text = "R$ ${menuItem.price}"
        }
    }
}
