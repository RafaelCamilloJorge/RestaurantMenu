package com.unaerp.restaurantmenu.Feature.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unaerp.restaurantmenu.Domain.MenuItem
import com.unaerp.restaurantmenu.core.use_case.menu.MenuUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MenuViewModel(private var menuUseCaseImpl: MenuUseCase) : ViewModel() {

    private val _recoverPasswordState = MutableStateFlow<Result<Map<String, List<MenuItem>>>?>(null)
    val recoverPasswordState = _recoverPasswordState.asStateFlow()

    fun getMenu() {
        viewModelScope.launch {
            val response = menuUseCaseImpl.getMenu()
            response.fold(onSuccess = { it ->
//                it.forEach {
//                    Log.v("type: ", it.key)
//                    it.value.forEach { item ->
//                        Log.v("product: ", item.name)
//                    }
//                }

                _recoverPasswordState.value = Result.success(it)
            }, onError = {
                _recoverPasswordState.value = Result.failure(it)
            })
        }
    }
}