// MainViewModel.kt
package com.unaerp.restaurantmenu.viewmodel

import androidx.lifecycle.*
import com.unaerp.restaurantmenu.core.repositories.auth.AuthRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginState = MutableStateFlow<Result<Unit>?>(null)
    val loginState = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = authRepository.loginWithEmailAndPassword(email, password)
            result.fold(onSuccess = {}, onError = {})
        }
    }
}
