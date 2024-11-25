package com.unaerp.restaurantmenu.core.repositories.user_data

import com.unaerp.restaurantmenu.Domain.ResponseShoppingCartItem
import com.unaerp.restaurantmenu.Domain.UserAuth
import com.unaerp.restaurantmenu.core.results.OnResult

interface UserDataRepository {
    suspend fun createUser(userAuth: UserAuth, name: String): OnResult<Unit>

    suspend fun getCardUser(id: String): OnResult<List<ResponseShoppingCartItem>>

    suspend fun finishPurchase(id: String): OnResult<Unit>

    suspend fun setQuantityProduct(idUser: String, idProduct: String, quantity: Long): OnResult<Unit>
}