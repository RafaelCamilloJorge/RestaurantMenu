package com.unaerp.restaurantmenu.Feature.cart

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.unaerp.restaurantmenu.Domain.CartItem
import com.unaerp.restaurantmenu.databinding.ActivityCartBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter
    private val viewModel: CartViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewModel()
        val initialCartItems = mutableListOf<CartItem>()
        viewModel.getCartUser()

        cartAdapter = CartAdapter(
            cartItems = initialCartItems,
            viewModel = viewModel,
            onUpdateTotal = { updateTotal() })

        binding.confirmButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Finalizar Compra")
            builder.setMessage("Você tem certeza que deseja finalizar a compra?")

            builder.setPositiveButton("Sim") { dialog, which ->
                viewModel.finishPurchase()
            }

            builder.setNegativeButton("Não") { dialog, which ->
                dialog.dismiss()
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        binding.cartRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.cartRecyclerView.adapter = cartAdapter

        updateTotal()
    }

    private fun updateTotal() {
        val total = cartAdapter.getTotalPrice()
        binding.totalPrice.text = "Total: R$ %.2f".format(total)
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.cartState.collect { result ->
                result?.onSuccess { flatList: List<CartItem> ->
                    cartAdapter.updateList(flatList)
                    updateTotal()
                }?.onFailure { error ->
                    Toast.makeText(
                        this@CartActivity,
                        "Erro ao carregar carrinho: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.purchaseState.collect { result ->
                result?.onSuccess {
                    Toast.makeText(this@CartActivity, "Pedido enviado com sucesso!", Toast.LENGTH_SHORT).show()
                    cartAdapter.updateList(emptyList())
                    updateTotal()
                }?.onFailure { error ->
                    Toast.makeText(
                        this@CartActivity,
                        "Erro ao finalizar compra: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }
}
