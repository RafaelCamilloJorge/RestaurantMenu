package com.unaerp.restaurantmenu.Feature.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unaerp.restaurantmenu.Domain.MenuCategory
import com.unaerp.restaurantmenu.Domain.MenuItem
import com.unaerp.restaurantmenu.databinding.ItemCategoryBinding
import com.unaerp.restaurantmenu.databinding.ItemMenuItemBinding
import java.lang.IllegalArgumentException

class MenuRecyclerViewAdapter : RecyclerView.Adapter<MenuRecyclerViewHolder>() {

    private var list = listOf<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuRecyclerViewHolder =
        when (viewType) {
            TYPE_CATEGORY -> MenuRecyclerViewHolder.CategoryViewHolder(
                ItemCategoryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            TYPE_ITEM -> MenuRecyclerViewHolder.ItemViewHolder(
                ItemMenuItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Tipo de View Inválido")
        }

    override fun onBindViewHolder(holder: MenuRecyclerViewHolder, position: Int) {
        when (holder) {
            is MenuRecyclerViewHolder.CategoryViewHolder -> holder.bind(list[position] as MenuCategory)
            is MenuRecyclerViewHolder.ItemViewHolder -> holder.bind(list[position] as MenuItem)
        }
    }

    override fun getItemViewType(position: Int): Int = when (list[position]) {
        is MenuCategory -> TYPE_CATEGORY
        is MenuItem -> TYPE_ITEM
        else -> throw IllegalArgumentException("Tipo de View Inválido")

    }

    override fun getItemCount(): Int = list.size

    fun setData(listaToSet: List<Any>) {
        this.list = listaToSet
        notifyDataSetChanged()
    }

    companion object {
        const val TYPE_CATEGORY = 0
        const val TYPE_ITEM = 1
    }
}
