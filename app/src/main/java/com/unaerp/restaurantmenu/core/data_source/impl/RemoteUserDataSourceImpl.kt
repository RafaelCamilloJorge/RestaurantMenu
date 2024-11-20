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
        try {
            val mapUser = mutableMapOf<String, Any>()

            mapUser["id"] = userAuth.uid
            mapUser["name"] = name
            mapUser["email"] = userAuth.email
            mapUser["shopping_cart"] = emptyList<Map<String, Any>>()

            db.collection("users").document(userAuth.uid).set(mapUser)
            return OnResult.Success(Unit)
        } catch (error: FirebaseFirestoreException) {
            return OnResult.Error(GenericError(error.message))
        }
    }

    override suspend fun getCartUser(uid: String): OnResult<List<ResponseShoppingCartItem>> {
        try {
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
            return OnResult.Error(GenericError(error.message))
        }
    }

    override suspend fun finishPurchase(id: String): OnResult<Unit> {
        return try {
            db.collection("users").document(id)
                .update("shopping_cart", emptyList<Map<String, Any>>())

            return OnResult.Success(Unit)
        } catch (error: FirebaseFirestoreException) {
            OnResult.Error(GenericError(error.message))
        }
    }

    override suspend fun setProductCart(
        idUser: String, idProduct: String, quantity: Int
    ): OnResult<Unit> {
        try {
            val responseGetCart = getCartUser(idUser)

            if (responseGetCart is OnResult.Error) return OnResult.Error(responseGetCart.exception)

            if (responseGetCart is OnResult.Success) {
                val listCart: MutableList<ResponseShoppingCartItem> =
                    responseGetCart.data.toMutableList()

                var productExist: Boolean = false
                listCart.forEach {
                    if (it.id == idProduct) {
                        productExist = true
                        it.quantity = quantity
                    }
                }
                if (!productExist) {
                    listCart.add(ResponseShoppingCartItem(idProduct, quantity))
                }

                val listCartMapped: MutableList<Map<String, Any>> = mutableListOf()

                listCart.forEach { listCartMapped.add(it.toMap()) }

                db.collection("users").document(idUser).update("shopping_cart", listCartMapped)
            }
            return OnResult.Success(Unit)
        } catch (error: FirebaseFirestoreException) {
            return OnResult.Error(GenericError(error.message))
        }
    }
}