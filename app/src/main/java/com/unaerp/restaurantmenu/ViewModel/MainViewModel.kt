// MainViewModel.kt
package com.unaerp.restaurantmenu.viewmodel

import androidx.lifecycle.*
import com.unaerp.restaurantmenu.core.use_case.auth.impl.AuthUseCaseImpl
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(private val authUseCaseImpl: AuthUseCaseImpl) : ViewModel() {

//    private val _loginState = MutableStateFlow<Result<Unit>?>(null)
//    val loginState = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                val result = authUseCaseImpl.loginWithEmailAndPassword(email, password)
                result.fold(onSuccess = {}, onError = {})
            }
            // Atualizar a UI na thread principal
        }
    }
}
