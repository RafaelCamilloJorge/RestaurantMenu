package com.unaerp.restaurantmenu.core.repositories.order

import com.unaerp.restaurantmenu.core.results.OnResult

interface OrderRepository {
    suspend fun removeItemInShoppingCar(idItem: String, idUser: String): OnResult<Unit>

    suspend fun getTotalValueOfShoppingCar(
        idUser: String
    ): OnResult<Double>
}