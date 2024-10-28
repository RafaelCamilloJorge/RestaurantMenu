package com.unaerp.restaurantmenu.Feature.menu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.unaerp.restaurantmenu.databinding.ActivityMenuBinding
import com.unaerp.restaurantmenu.Domain.MenuCategory
import com.unaerp.restaurantmenu.Domain.MenuItem
import com.unaerp.restaurantmenu.R

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private lateinit var adapter: MenuRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MenuRecyclerViewAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        val teste = listOf(
            MenuCategory(1, "Entradas", emptyList()),
            MenuItem(1, "Bruschetta", "Tomate e manjericão", 15.0, R.drawable.ic_launcher_background.toString()),
            MenuItem(2, "Carpaccio", "Carne bovina com parmesão", 30.0, R.drawable.ic_launcher_background.toString()),
            MenuCategory(2, "Pratos Principais", emptyList()),
            MenuItem(3, "Bife à Parmegiana", "Com arroz e fritas", 45.0, R.drawable.ic_launcher_background.toString())
        )

        adapter.setData(teste)
    }
}
