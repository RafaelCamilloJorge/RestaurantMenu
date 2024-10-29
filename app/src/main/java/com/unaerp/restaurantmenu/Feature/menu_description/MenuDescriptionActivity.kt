package com.unaerp.restaurantmenu.Feature.menu_description

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.unaerp.restaurantmenu.databinding.ActivityMenuDescriptionBinding

class MenuDescriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
