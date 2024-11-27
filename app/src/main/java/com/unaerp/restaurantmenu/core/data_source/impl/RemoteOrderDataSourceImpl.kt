package com.unaerp.restaurantmenu.core.data_source.impl

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.unaerp.restaurantmenu.Domain.ResponseMenuItem
import com.unaerp.restaurantmenu.core.data_source.RemoteOrderDataSource
import com.unaerp.restaurantmenu.core.errors.GenericError
import com.unaerp.restaurantmenu.core.results.OnResult
import kotlinx.coroutines.tasks.await

class RemoteOrderDataSourceImpl(private val db: FirebaseFirestore) : RemoteOrderDataSource {
    override suspend fun removeItemInShoppingCar(idItem: String, idUser: String): OnResult<Unit> {
        return try {
            val docRefUser: DocumentReference = db.collection("users").document(idUser)
            val documentSnapshot = docRefUser.get().await()

            val shoppingCar = documentSnapshot.get("shopping_cart") as? MutableList<MutableMap<String, Any>>
                ?: mutableListOf()

            shoppingCar.removeIf { item -> item["id"] == idItem }
            docRefUser.update("shopping_cart", shoppingCar).await()

            OnResult.Success(Unit)
        } catch (error: FirebaseFirestoreException) {
            OnResult.Error(GenericError("Erro ao remover item"))
        }
    }

    override suspend fun getTotalValueOfShoppingCar(
        idUser: String
    ): OnResult<Double> {
        try {
            var totalValue = 0.0
            val docRefUser: DocumentReference = db.collection("users").document(idUser)
            docRefUser.get().addOnCompleteListener { doc ->
                val shoppingCar: MutableList<MutableMap<String, Any>> =
                    doc.result.get("shopping_cart") as MutableList<MutableMap<String, Any>>
                shoppingCar.forEach { item ->
                    totalValue += item["price"].toString().toDouble()
                }
            }
            return OnResult.Success(totalValue)
        } catch (error: FirebaseFirestoreException) {
            return OnResult.Error(GenericError(error.message))
        }
    }
}