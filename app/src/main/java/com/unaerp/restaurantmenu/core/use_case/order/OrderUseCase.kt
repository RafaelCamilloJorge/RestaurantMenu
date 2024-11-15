package com.unaerp.restaurantmenu.core.use_case.order

import com.unaerp.restaurantmenu.Domain.MenuItem
import com.unaerp.restaurantmenu.core.results.OnResult

interface OrderUseCase {
    suspend fun addItemInShoppingCar(item: MenuItem): OnResult<Unit>

    suspend fun removeItemInShoppingCar(idItem: String): OnResult<Unit>

    suspend fun editQuantityOfItemInShoppingCar(
        idItem: String, newQuantity: Int
    ): OnResult<Unit>

    suspend fun getTotalValueOfShoppingCar(): OnResult<Double>
}