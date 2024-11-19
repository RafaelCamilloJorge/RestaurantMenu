package com.unaerp.restaurantmenu.core.data_source.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.unaerp.restaurantmenu.Domain.ResponseShoppingCartItem
import com.unaerp.restaurantmenu.Domain.UserAuth
import com.unaerp.restaurantmenu.core.data_source.RemoteUserDataSource
import com.unaerp.restaurantmenu.core.errors.GenericError
import com.unaerp.restaurantmenu.core.results.OnResult
import kotlinx.coroutines.tasks.await

class RemoteUserDataSourceImpl(private var db: FirebaseFirestore) : RemoteUserDataSource {
    override suspend fun createUser(userAuth: UserAuth, name: String): OnResult<Unit> {
        return try {
            val mapUser = mutableMapOf<String, Any>()

            mapUser["id"] = userAuth.uid
            mapUser["name"] = name
            mapUser["email"] = userAuth.email
            mapUser["shopping_cart"] = emptyList<Map<String, Any>>()

            db.collection("users").document(userAuth.uid).set(mapUser)
            return OnResult.Success(Unit)
        } catch (error: FirebaseFirestoreException) {
            OnResult.Error(GenericError(error.message))
        }
    }

    override suspend fun getCartUser(uid: String): OnResult<List<ResponseShoppingCartItem>> {
        return try {
            val listShoppingCartItem = mutableListOf<ResponseShoppingCartItem>()

            val response = db.collection("users").document(uid).get().await()

            if (response.exists()) {
                val cartMap = response.get("shopping_cart") as List<Map<String, Any>>
                cartMap.forEach {
                    listShoppingCartItem.add(ResponseShoppingCartItem.fromMap(it))
                }
            }

            return OnResult.Success(listShoppingCartItem)
        } catch (error: FirebaseFirestoreException) {
            OnResult.Error(GenericError(error.message))
        }
    }
}