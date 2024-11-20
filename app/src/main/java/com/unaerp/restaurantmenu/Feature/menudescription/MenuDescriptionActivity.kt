package com.unaerp.restaurantmenu.Feature.menudescription

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.unaerp.restaurantmenu.databinding.ActivityMenuDescriptionBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuDescriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuDescriptionBinding
    private val menuDescriptionViewModel: MenuDescriptionViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMenuDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val description = intent.getStringExtra("description")
        val price = intent.getDoubleExtra("price", 0.0)

        binding.name.text = name
        binding.description.text = description
        binding.price.text = "Preço " + price.toString().format("%.2f")
    }
}
