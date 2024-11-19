package com.unaerp.restaurantmenu.Feature.ForgotPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unaerp.restaurantmenu.core.use_case.auth.AuthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(private val authUseCase: AuthUseCase) : ViewModel() {

    private val _recoverPasswordState = MutableStateFlow(Result.success(Unit))
    val recoverPasswordState = _recoverPasswordState.asStateFlow()

    fun recoverPassword(email: String) {
        viewModelScope.launch {
            try {
                val result = authUseCase.forgotPassword(email)
                result.fold(
                    onSuccess = {
                        _recoverPasswordState.value = Result.success(Unit)
                    },
                    onError = { error ->
                        _recoverPasswordState.value = Result.failure(error)
                    }
                )
            } catch (e: Exception) {
                _recoverPasswordState.value = Result.failure(Exception("Failed to send recovery email. Please try again."))
            }
        }
    }
}
