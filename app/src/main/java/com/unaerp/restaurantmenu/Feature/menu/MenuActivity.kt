package com.unaerp.restaurantmenu.Feature.menu

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.unaerp.restaurantmenu.databinding.ActivityMenuBinding
import com.unaerp.restaurantmenu.Domain.ResponseMenuItem
import com.unaerp.restaurantmenu.Feature.cart.CartActivity
import com.unaerp.restaurantmenu.Feature.menudescription.MenuDescriptionActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private lateinit var adapter: MenuRecyclerViewAdapter
    private val viewModel: MenuViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        observeViewModel()
        viewModel.getMenu()

        binding.cart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        binding.logout.setOnClickListener {
            viewModel.logout()
            finish()
        }
    }

    private fun setupRecyclerView() {
        adapter = MenuRecyclerViewAdapter(emptyList()) { item ->
            if (item is ResponseMenuItem) {
                val intent = Intent(this, MenuDescriptionActivity::class.java)
                intent.putExtra("id", item.id)
                intent.putExtra("name", item.name)
                intent.putExtra("description", item.description)
                intent.putExtra("image", item.image)
                intent.putExtra("price", item.price)
                startActivity(intent)
            }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.menuState.collect { result ->
                result?.onSuccess { flatList: List<Any> ->
                    adapter.updateList(flatList)
                }?.onFailure { error ->
                    Toast.makeText(
                        this@MenuActivity,
                        "Erro ao carregar menu: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.loginState.collect { result ->
                result?.onSuccess {
                    Toast.makeText(this@MenuActivity, "Logout realizado com sucesso", Toast.LENGTH_SHORT).show()
                }?.onFailure { error ->
                    Toast.makeText(this@MenuActivity, "Erro ao realizar logout: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
