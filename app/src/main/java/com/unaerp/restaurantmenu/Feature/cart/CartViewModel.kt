package com.unaerp.restaurantmenu.Feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unaerp.restaurantmenu.Domain.CartItem
import com.unaerp.restaurantmenu.core.use_case.order.OrderUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(private var orderUseCase: OrderUseCase) : ViewModel() {

    private val _cartState = MutableStateFlow<Result<List<CartItem>>?>(null)
    val cartState = _cartState.asStateFlow()

    private val _purchaseState = MutableStateFlow<Result<Unit>?>(null)
    val purchaseState = _purchaseState.asStateFlow()

    fun finishPurchase() {
        viewModelScope.launch {
            val response = orderUseCase.finishPurchase()
            response.fold(
                onSuccess = {
                    _purchaseState.value = Result.success(Unit)
                },
                onError = { error ->
                    _purchaseState.value = Result.failure(error)
                }
            )
        }
    }

    fun getCartUser() {
        viewModelScope.launch {
            var response = orderUseCase.getMyCart()
            response.fold(
                onSuccess = {
                    _cartState.value = Result.success(it.items)
            }, onError = { error ->
                _cartState.value = Result.failure(error)
            })
        }
    }
}