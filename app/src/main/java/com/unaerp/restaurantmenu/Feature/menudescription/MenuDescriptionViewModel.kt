package com.unaerp.restaurantmenu.Feature.menudescription

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unaerp.restaurantmenu.Domain.ResponseMenuItem
import com.unaerp.restaurantmenu.core.use_case.order.OrderUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MenuDescriptionViewModel(private var orderUseCaseImpl: OrderUseCase) : ViewModel() {
    private val _recoverPasswordState = MutableStateFlow(Result.success(Unit))
    val recoverPasswordState = _recoverPasswordState.asStateFlow()

    fun addItemInCart(menuItem: ResponseMenuItem, quantity: Int) {
        viewModelScope.launch {
            val response = orderUseCaseImpl.itemInShoppingCar(menuItem, quantity)
            response.fold(onSuccess = {
                _recoverPasswordState.value = Result.success(it)
            }, onError = {

            })
        }
    }
}