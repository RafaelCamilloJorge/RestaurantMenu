package com.unaerp.restaurantmenu.Feature.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unaerp.restaurantmenu.core.use_case.auth.impl.AuthUseCaseImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private var authUseCaseImpl: AuthUseCaseImpl) : ViewModel() {

    private val _registerState = MutableStateFlow<Result<Unit>?>(null)
    val registerState = _registerState.asStateFlow()

    fun registerAccount(email: String, password: String, name: String) {
        viewModelScope.launch {
            val response = authUseCaseImpl.createAccountWithEmailAndPassword(email, password, name)
            response.fold(onSuccess = {
                println("Conta criada")
                _registerState.value = Result.success(it)
            }, onError = {
                _registerState.value = Result.failure(it)
            })
        }

    }
}