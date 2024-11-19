package com.unaerp.restaurantmenu.Feature.menu

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.unaerp.restaurantmenu.databinding.ActivityMenuBinding
import com.unaerp.restaurantmenu.Domain.MenuCategory
import com.unaerp.restaurantmenu.Domain.ResponseMenuItem
import com.unaerp.restaurantmenu.Feature.menu_description.MenuDescriptionActivity
import com.unaerp.restaurantmenu.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private lateinit var adapter: MenuRecyclerViewAdapter
    private val viewModel: MenuViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getMenu()
        observeViewModel()

        binding.cart.setOnClickListener {
            Toast.makeText(this, "Cart", Toast.LENGTH_SHORT).show()
        }

        val teste = listOf(
            MenuCategory(1, "Entradas", emptyList()),
            ResponseMenuItem("1", "Bruschetta", "Tomate e manjericão", 15.0, R.drawable.ic_launcher_background.toString(),""),
            ResponseMenuItem("2", "Carpaccio", "Carne bovina com parmesão", 30.0, R.drawable.ic_launcher_background.toString(),""),
            MenuCategory(2, "Pratos Principais", emptyList()),
            ResponseMenuItem("3", "Bife à Parmegiana", "Com arroz e fritas", 45.0, R.drawable.ic_launcher_background.toString(),""),
            MenuCategory(3, "Pratos Veganos", emptyList()),
            ResponseMenuItem("3", "Arvore", "Arvore com pitadas de terra", 0.50, R.drawable.ic_launcher_background.toString(),""),
            ResponseMenuItem("3", "Girassol", "Girassol com petalas de rosa", 5.0, R.drawable.ic_launcher_background.toString(),"")
        )

        adapter = MenuRecyclerViewAdapter(teste) { item ->
            val intent = Intent(this, MenuDescriptionActivity::class.java)
            intent.putExtra("name", (item as? ResponseMenuItem)?.name)
            intent.putExtra("description", (item as? ResponseMenuItem)?.description)
            intent.putExtra("price", (item as? ResponseMenuItem)?.price)
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        adapter.updateList(teste)
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.recoverPasswordState.collect { result ->
                result?.onSuccess {
                    it
                }?.onFailure { error ->
                    Toast.makeText(
                        this@MenuActivity,
                        "Falha na autenticação: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
