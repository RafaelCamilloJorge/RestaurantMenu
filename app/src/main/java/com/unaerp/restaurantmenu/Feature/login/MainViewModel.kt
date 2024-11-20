package com.unaerp.restaurantmenu.Feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unaerp.restaurantmenu.core.use_case.auth.AuthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val authUseCaseImpl: AuthUseCase) : ViewModel() {

    private val _loginState = MutableStateFlow<Result<Unit>?>(null)
    val loginState = _loginState.asStateFlow()

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
}
