package com.unaerp.restaurantmenu.Feature.menudescription

import MenuDescriptionViewModel
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.unaerp.restaurantmenu.Domain.ResponseMenuItem
import com.unaerp.restaurantmenu.R
import com.unaerp.restaurantmenu.databinding.ActivityMenuDescriptionBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.bumptech.glide.Glide
import com.google.firebase.storage.StorageReference

class MenuDescriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuDescriptionBinding
    private val menuDescriptionViewModel: MenuDescriptionViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMenuDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("id") ?: ""
        val name = intent.getStringExtra("name") ?: "Produto Desconhecido"
        val description = intent.getStringExtra("description") ?: ""
        val price = intent.getDoubleExtra("price", 0.00)
        val image = intent.getStringExtra("image") ?: ""
        val type = intent.getStringExtra("type") ?: "outro"

        var quantity = binding.itemQuantity.text.toString().toLong()

        binding.name.text = name
        binding.description.text = description
        binding.price.text = "Preço  R\$ " + price.toString().format("%.2f")

        val storageReference: StorageReference = Firebase.storage.reference
        val imageRef = storageReference.child(image)
        imageRef.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(this@MenuDescriptionActivity).load(uri).error(R.drawable.ic_password)
                .into(binding.image)
        }

        binding.increaseButton.setOnClickListener {
            binding.itemQuantity.text = (quantity + 1).toString()
            quantity = binding.itemQuantity.text.toString().toLong()
        }

        binding.decreaseButton.setOnClickListener {
            if (quantity > 1) {
                binding.itemQuantity.text = (quantity - 1).toString()
            }
            quantity = binding.itemQuantity.text.toString().toLong()
        }

        binding.addToCartButton.setOnClickListener {
            val menuItem = ResponseMenuItem(
                id = id,
                name = name,
                description = description,
                price = price,
                image = image,
                type = type
            )

            menuDescriptionViewModel.addItemInCart(menuItem, quantity)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            menuDescriptionViewModel.cartState.collect { result ->
                result?.onSuccess {
                    Toast.makeText(
                        this@MenuDescriptionActivity,
                        "Item adicionado ao carrinho!",
                        Toast.LENGTH_SHORT
                    ).show()
                }?.onFailure { error ->
                    Toast.makeText(
                        this@MenuDescriptionActivity,
                        "Erro ao adicionar ao carrinho: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}
