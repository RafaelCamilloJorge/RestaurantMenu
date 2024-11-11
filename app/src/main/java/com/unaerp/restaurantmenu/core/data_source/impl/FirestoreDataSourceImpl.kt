package com.unaerp.restaurantmenu.core.data_source.impl

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.unaerp.restaurantmenu.Domain.MenuCategory
import com.unaerp.restaurantmenu.Domain.MenuItem
import com.unaerp.restaurantmenu.core.data_source.FirestoreDataSource
import com.unaerp.restaurantmenu.core.repositories.errors.GenericError
import com.unaerp.restaurantmenu.core.results.OnResult
import kotlinx.coroutines.tasks.await

class FirestoreDataSourceImpl : FirestoreDataSource {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override suspend fun createUser(id: String, email: String, name: String): OnResult<Unit> {
        return try {
            val docRefUser: DocumentReference = db.collection("users").document(id)
            docRefUser.set(
                mapOf(
                    "id" to id,
                    "email" to email,
                    "name" to name,
                    "shopping_cart" to arrayOf<Map<String, Any>>()
                )
            ).await()
            OnResult.Success(Unit)
        } catch (error: FirebaseFirestoreException) {
            OnResult.Error(GenericError(error.message))
        }
    }

    override suspend fun getMenu(): OnResult<List<MenuCategory>> {
        try {
            val listMenu: MutableList<MenuCategory> = mutableListOf()
            db.collection("menu").get().addOnSuccessListener { result ->
                for (doc in result) {
                    listMenu.add(MenuCategory.fromCollection(doc))
                }
            }.await()
            return OnResult.Success(listMenu)
        } catch (error: FirebaseFirestoreException) {
            return OnResult.Error(GenericError(error.message))
        }
    }

    override suspend fun addItemInShoppingCar(item: MenuItem, idUser: String): OnResult<Unit> {
        try {
            val docRefUser: DocumentReference = db.collection("users").document(idUser)
            docRefUser.update("shopping_cart", FieldValue.arrayUnion(item.toMap())).await()
            return OnResult.Success(Unit)
        } catch (error: FirebaseFirestoreException) {
            return OnResult.Error(GenericError(error.message))
        }
    }

    override suspend fun removeItemInShoppingCar(idItem: String, idUser: String): OnResult<Unit> {
        try {
            val docRefUser: DocumentReference = db.collection("users").document(idUser)
            docRefUser.get().addOnCompleteListener { doc ->
                val shoppingCar: MutableList<MutableMap<String, Any>> =
                    doc.result.get("shopping_cart") as MutableList<MutableMap<String, Any>>
                shoppingCar.removeIf { item -> item.getValue("id") == idItem }
                docRefUser.update("shopping_cart", shoppingCar)
            }
            return OnResult.Success(Unit)
        } catch (error: FirebaseFirestoreException) {
            return OnResult.Error(GenericError("Erro ao remover item"))
        }
    }

    override suspend fun editQuantityOfItemInShoppingCar(
        idItem: String, newQuantity: Int, idUser: String
    ): OnResult<Unit> {
        try {
            val docRefUser: DocumentReference = db.collection("users").document(idUser)
            docRefUser.get().addOnCompleteListener { doc ->
                val shoppingCar: MutableList<MutableMap<String, Any>> =
                    doc.result.get("shopping_cart") as MutableList<MutableMap<String, Any>>
                shoppingCar.forEach { item ->
                    if (item.getValue("id") == idItem) {
                        item["quantity"] = newQuantity
                    }
                }
                docRefUser.update("shopping_cart", shoppingCar)
            }
            return OnResult.Success(Unit)
        } catch (error: FirebaseFirestoreException) {
            return OnResult.Error(GenericError(error.message))
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