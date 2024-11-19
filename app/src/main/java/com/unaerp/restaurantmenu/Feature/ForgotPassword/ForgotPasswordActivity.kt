package com.unaerp.restaurantmenu.Feature.ForgotPassword

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.unaerp.restaurantmenu.databinding.ActivityForgotPasswordBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private val viewModel: ForgotPasswordViewModel by viewModel()

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

        observeViewModel(this)
    }

    private fun observeViewModel(context: ForgotPasswordActivity) {
        lifecycleScope.launchWhenStarted {
            viewModel.recoverPasswordState.collect { result ->
                result?.onSuccess {
                    Toast.makeText(context, "E-mail de recuperação enviado.", Toast.LENGTH_SHORT).show()
                    finish()
                }?.onFailure {
                    Toast.makeText(context, "Erro ao enviar e-mail: ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
