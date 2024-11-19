package com.unaerp.restaurantmenu.Feature.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.unaerp.restaurantmenu.Feature.ForgotPassword.ForgotPasswordActivity
import com.unaerp.restaurantmenu.Feature.menu.MenuActivity
import com.unaerp.restaurantmenu.databinding.ActivityMainBinding
import com.unaerp.restaurantmenu.R
import com.unaerp.restaurantmenu.Feature.register.RegisterActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        observeViewModel()

        binding.btnLogin.setOnClickListener {
            val email = binding.fieldEmail.text.toString().trim()
            val password = binding.fieldPassword.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(email, password)
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.loginState.collect { result ->
                result?.onSuccess {
                    Toast.makeText(this@MainActivity, "Autenticação bem-sucedida.", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@MainActivity, MenuActivity::class.java))
                }?.onFailure { error ->
                    Toast.makeText(this@MainActivity, "Falha na autenticação: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
