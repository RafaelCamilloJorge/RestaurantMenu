package com.unaerp.restaurantmenu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unaerp.restaurantmenu.core.use_case.auth.impl.AuthUseCaseImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val authUseCaseImpl: AuthUseCaseImpl) : ViewModel() {


    private val _loginState = MutableStateFlow<Result<Unit>?>(null)
    val loginState = _loginState.asStateFlow()

    private val _recoverPasswordState = MutableStateFlow<Result<Unit>?>(null)
    val recoverPasswordState = _recoverPasswordState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val result = authUseCaseImpl.loginWithEmailAndPassword(email, password)
                result.fold(
                    onSuccess = {
                        _loginState.value = Result.success(Unit)
                    },
                    onError = { error ->
                        _loginState.value = Result.failure(error)
                    }
                )
            } catch (e: Exception) {
                _loginState.value = Result.failure(e)
            }
        }
    }

    fun recoverPassword(email: String) {
        viewModelScope.launch {
            try {
                val result = authUseCaseImpl.forgotPassword(email)
                result.fold(
                    onSuccess = {
                        _recoverPasswordState.value = Result.success(Unit)
                    },
                    onError = { error ->
                        _recoverPasswordState.value = Result.failure(error)
                    }
                )
            } catch (e: Exception) {
                _recoverPasswordState.value = Result.failure(e)
            }
        }
    }
}
