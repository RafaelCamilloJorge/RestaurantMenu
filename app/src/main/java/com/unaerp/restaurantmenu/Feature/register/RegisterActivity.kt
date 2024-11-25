package com.unaerp.restaurantmenu.Feature.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.unaerp.restaurantmenu.Feature.login.MainActivity
import com.unaerp.restaurantmenu.Feature.menu.MenuActivity
import com.unaerp.restaurantmenu.R
import com.unaerp.restaurantmenu.databinding.ActivityRegisterBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnRegister.setOnClickListener {
            val email = binding.fieldEmail.text.toString().trim()
            val password = binding.fieldPassword.text.toString().trim()
            val name = binding.fieldName.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()) {
                registerViewModel.registerAccount(email, password, name)
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            registerViewModel.registerState.collect { result ->
                result?.onSuccess {
                    Toast.makeText(
                        this@RegisterActivity, "Criação de conta bem-sucedida", Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this@RegisterActivity, MenuActivity::class.java))
                }?.onFailure { error ->
                    Toast.makeText(
                        this@RegisterActivity,
                        "Falha no registro: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
