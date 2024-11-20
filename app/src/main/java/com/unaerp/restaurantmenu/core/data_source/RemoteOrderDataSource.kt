package com.unaerp.restaurantmenu.core.data_source

import com.unaerp.restaurantmenu.Domain.ResponseMenuItem
import com.unaerp.restaurantmenu.core.results.OnResult

interface RemoteOrderDataSource {
    suspend fun removeItemInShoppingCar(idItem: String, idUser: String): OnResult<Unit>

    suspend fun getTotalValueOfShoppingCar(
        idUser: String
    ): OnResult<Double>

}