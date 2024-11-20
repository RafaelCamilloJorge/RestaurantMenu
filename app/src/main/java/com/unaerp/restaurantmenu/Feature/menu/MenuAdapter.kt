package com.unaerp.restaurantmenu.Feature.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.unaerp.restaurantmenu.databinding.ItemCategoryBinding
import com.unaerp.restaurantmenu.databinding.ItemMenuItemBinding
import com.unaerp.restaurantmenu.Domain.MenuCategory
import com.unaerp.restaurantmenu.Domain.ResponseMenuItem
import com.unaerp.restaurantmenu.R

class MenuRecyclerViewAdapter(
    private val items: List<Any>,
    private val onClick: (Any) -> Unit
) : RecyclerView.Adapter<MenuRecyclerViewAdapter.MenuRecyclerViewHolder>() {

    private val itemList = items.toMutableList()

    sealed class MenuRecyclerViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        class CategoryViewHolder(val binding: ItemCategoryBinding) : MenuRecyclerViewHolder(binding)
        class ItemViewHolder(val binding: ItemMenuItemBinding) : MenuRecyclerViewHolder(binding)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuRecyclerViewHolder {
        return when (viewType) {
            TIPO_CATEGORIA -> {
                val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MenuRecyclerViewHolder.CategoryViewHolder(binding)
            }
            TIPO_ITEM -> {
                val binding = ItemMenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MenuRecyclerViewHolder.ItemViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Tipo de View Inválido")
        }
    }

    override fun onBindViewHolder(holder: MenuRecyclerViewHolder, position: Int) {
        val item = itemList[position]
        when (holder) {
            is MenuRecyclerViewHolder.CategoryViewHolder -> {
                val category = item as MenuCategory
                holder.binding.categoryTitle.text = category.title
            }
            is MenuRecyclerViewHolder.ItemViewHolder -> {
                val menuItem = item as ResponseMenuItem
                holder.binding.itemName.text = menuItem.name
                holder.binding.itemImage.setImageResource(R.drawable.ic_launcher_foreground)
                holder.binding.itemPrice.text = "R$ %.2f".format(menuItem.price)
                holder.itemView.setOnClickListener { onClick(item) }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position]) {
            is MenuCategory -> TIPO_CATEGORIA
            is ResponseMenuItem -> TIPO_ITEM
            else -> throw IllegalArgumentException("Tipo de item desconhecido")
        }
    }

    override fun getItemCount(): Int = itemList.size

    fun updateList(newList: List<Any>) {
        itemList.clear()
        itemList.addAll(newList)
        notifyDataSetChanged()
    }

    companion object {
        const val TIPO_CATEGORIA = 0
        const val TIPO_ITEM = 1
    }
}
