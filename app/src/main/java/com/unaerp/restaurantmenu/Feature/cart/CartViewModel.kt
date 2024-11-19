package com.unaerp.restaurantmenu.Feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unaerp.restaurantmenu.core.use_case.order.OrderUseCase
import kotlinx.coroutines.launch

class CartViewModel(private var orderUseCase: OrderUseCase) : ViewModel() {
    fun getCartUser() {
        viewModelScope.launch {
            var response = orderUseCase.getMyCart()
            response.fold(onSuccess = {
                println("Valor total" + it.items.toString())
            }, onError = {
                println(it.messageError())
            })
        }
    }
}