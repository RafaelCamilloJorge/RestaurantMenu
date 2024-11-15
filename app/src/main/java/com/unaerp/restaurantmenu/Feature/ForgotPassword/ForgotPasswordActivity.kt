package com.unaerp.restaurantmenu.Feature.forgotpassword

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.unaerp.restaurantmenu.databinding.ActivityForgotPasswordBinding
import com.unaerp.restaurantmenu.Feature.login.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSend.setOnClickListener {
            val email = binding.fieldEmail.text.toString().trim()
            if (email.isNotEmpty()) {
                viewModel.recoverPassword(email)
            } else {
                Toast.makeText(this, "Por favor, preencha o campo de e-mail", Toast.LENGTH_SHORT).show()
            }
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.recoverPasswordState.collect { result ->
                result?.onSuccess {
                    Toast.makeText(this@ForgotPasswordActivity, "E-mail de recuperação enviado.", Toast.LENGTH_SHORT).show()
                    finish()
                }?.onFailure {
                    Toast.makeText(this@ForgotPasswordActivity, "Erro ao enviar e-mail: ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
