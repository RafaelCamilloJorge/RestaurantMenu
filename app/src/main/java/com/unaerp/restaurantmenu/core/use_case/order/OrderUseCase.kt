package com.unaerp.restaurantmenu.core.use_case.order

import com.unaerp.restaurantmenu.Domain.Cart
import com.unaerp.restaurantmenu.Domain.ResponseMenuItem
import com.unaerp.restaurantmenu.core.results.OnResult

interface OrderUseCase {
    suspend fun addItemInShoppingCar(item: ResponseMenuItem): OnResult<Unit>

    suspend fun removeItemInShoppingCar(idItem: String): OnResult<Unit>

    suspend fun editQuantityOfItemInShoppingCar(
        idItem: String, newQuantity: Int
    ): OnResult<Unit>

    suspend fun getTotalValueOfShoppingCar(): OnResult<Double>

    suspend fun getMyCart(): OnResult<Cart>

    suspend fun finishPurchase(): OnResult<Unit>
}