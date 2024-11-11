package com.unaerp.restaurantmenu.core.repositories.menu

import com.google.firebase.firestore.FirebaseFirestore
import com.unaerp.restaurantmenu.Domain.MenuCategory
import com.unaerp.restaurantmenu.Domain.MenuItem
import com.unaerp.restaurantmenu.core.results.OnResult

interface MenuRepository {

    suspend fun getMenu(): OnResult<List<MenuCategory>>

    suspend fun addItemInShoppingCar(item: MenuItem): OnResult<Unit>

    suspend fun removeItemInShoppingCar(idItem: String): OnResult<Unit>

    suspend fun editQuantityOfItemInShoppingCar(
        idItem: String, newQuantity: Int
    ): OnResult<Unit>

    suspend fun getTotalValueOfShoppingCar(): OnResult<Double>
}