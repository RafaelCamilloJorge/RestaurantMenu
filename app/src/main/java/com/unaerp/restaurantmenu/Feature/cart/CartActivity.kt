package com.unaerp.restaurantmenu.Feature.cart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.unaerp.restaurantmenu.Domain.CartItem
import com.unaerp.restaurantmenu.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val teste = MutableList(10) { CartItem(1, "teste", 10.0, 1) }

        cartAdapter = CartAdapter(teste, ::updateTotal)
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.cartRecyclerView.adapter = cartAdapter



        updateTotal()
    }

    private fun updateTotal() {
        val total = cartAdapter.getTotalPrice()
        binding.totalPrice.text = "Total: R$ %.2f".format(total)
    }
}
