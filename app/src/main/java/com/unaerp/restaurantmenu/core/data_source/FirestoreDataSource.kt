package com.unaerp.restaurantmenu.core.data_source

import com.unaerp.restaurantmenu.Domain.MenuCategory
import com.unaerp.restaurantmenu.Domain.MenuItem
import com.unaerp.restaurantmenu.core.results.OnResult

interface FirestoreDataSource {

    suspend fun createUser(id: String, email: String, name: String): OnResult<Unit>

    suspend fun getMenu(): OnResult<List<MenuCategory>>

    suspend fun addItemInShoppingCar(item: MenuItem, idUser: String): OnResult<Unit>

    suspend fun removeItemInShoppingCar(idItem: String, idUser: String): OnResult<Unit>

    suspend fun editQuantityOfItemInShoppingCar(
        idItem: String, newQuantity: Int, idUser: String
    ): OnResult<Unit>

    suspend fun getTotalValueOfShoppingCar(
        idUser: String
    ): OnResult<Double>
}