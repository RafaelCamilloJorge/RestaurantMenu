package com.unaerp.restaurantmenu.Feature.menu

import android.os.Bundle
import android.widget.Toast
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

        val teste = listOf(
            MenuCategory(1, "Entradas", emptyList()),
            MenuItem(1, "Bruschetta", "Tomate e manjericão", 15.0, R.drawable.ic_launcher_background.toString()),
            MenuItem(2, "Carpaccio", "Carne bovina com parmesão", 30.0, R.drawable.ic_launcher_background.toString()),
            MenuCategory(2, "Pratos Principais", emptyList()),
            MenuItem(3, "Bife à Parmegiana", "Com arroz e fritas", 45.0, R.drawable.ic_launcher_background.toString()),
            MenuCategory(3, "Pratos Veganos", emptyList()),
            MenuItem(3, "Arvore", "Arvore com pitadas de terra", 0.50, R.drawable.ic_launcher_background.toString()),
            MenuItem(3, "Girassol", "Girassol com petalas de rosa", 5.0, R.drawable.ic_launcher_background.toString())
        )

        adapter = MenuRecyclerViewAdapter(
            items = teste,
            onClick = { item ->
                when (item) {
                    is MenuCategory -> Toast.makeText(this, "Categoria: ${item.title}", Toast.LENGTH_SHORT).show()
                    is MenuItem -> Toast.makeText(this, "Item: ${item.name} - ${item.price}", Toast.LENGTH_SHORT).show()
                }
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        adapter.updateList(teste)
    }
}
