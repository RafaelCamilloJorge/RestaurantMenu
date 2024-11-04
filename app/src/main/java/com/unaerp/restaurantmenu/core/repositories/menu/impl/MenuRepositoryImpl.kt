package com.unaerp.restaurantmenu.core.repositories.menu.impl

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.unaerp.restaurantmenu.Domain.MenuCategory
import com.unaerp.restaurantmenu.Domain.MenuItem
import com.unaerp.restaurantmenu.core.repositories.errors.GenericError
import com.unaerp.restaurantmenu.core.repositories.menu.MenuRepository
import com.unaerp.restaurantmenu.core.results.OnResult

class MenuRepositoryImpl(override val db: FirebaseFirestore) : MenuRepository {
    override suspend fun getMenu(): OnResult<List<MenuCategory>> {
        try {
            val listMenu: MutableList<MenuCategory> = mutableListOf()
            db.collection("menu").get().addOnSuccessListener { result ->
                for (doc in result) {
                    listMenu.add(MenuCategory.fromCollection(doc))
                }
            }
            return OnResult.Success(listMenu)
        } catch (e: Error) {
            return OnResult.Error(GenericError("Erro ao buscar o cardápio"))
        }
    }

    override suspend fun addItemInShoppingCar(item: MenuItem, idUser: String): OnResult<Unit> {
        try {
            val docRefUser: DocumentReference = db.collection("users").document(idUser)
            docRefUser.update("shopping_cart", FieldValue.arrayUnion(item.toMap()))
            return OnResult.Success(Unit)
        } catch (error: Error) {
            return OnResult.Error(GenericError("Erro ao adicionar item"))
        }
    }

    override suspend fun removeItemInShoppingCar(idItem: String, idUser: String): OnResult<Unit> {
        try {
            val docRefUser: DocumentReference = db.collection("users").document(idUser)
            val currentCart = docRefUser.get() as MutableList<Map<String, Any>>
            currentCart.removeIf { item -> item.getValue("id") == idItem }
            docRefUser.update("shopping_cart", currentCart)
            return OnResult.Success(Unit)
        } catch (error: Error) {
            return OnResult.Error(GenericError("Erro ao remover item"))
        }
    }

    override suspend fun editQuantityOfItemInShoppingCar(
        idItem: String, newQuantity: Int, idUser: String
    ): OnResult<Unit> {
        try {
            val docRefUser: DocumentReference = db.collection("users").document(idUser)
            val currentCart = docRefUser.get() as MutableList<MutableMap<String, Any>>
            currentCart.forEach { item ->
                if (item.getValue("id") == idItem) {
                    item["quantity"] = newQuantity
                }
            }
            docRefUser.update("shopping_cart", currentCart)
            return OnResult.Success(Unit)
        } catch (error: Error) {
            return OnResult.Error(GenericError("Erro ao editar item"))
        }
    }

    override suspend fun getTotalValueOfShoppingCar(
        idUser: String
    ): OnResult<Double> {
        try {
            var totalValue = 0.0
            val docRefUser: DocumentReference = db.collection("users").document(idUser)
            val currentCart = docRefUser.get() as MutableList<MutableMap<String, Any>>
            currentCart.forEach { item ->
                totalValue += item["price"].toString().toDouble()
            }
            docRefUser.update("shopping_cart", currentCart)
            return OnResult.Success(totalValue)
        } catch (error: Error) {
            return OnResult.Error(GenericError("Erro ao ao buscar o valor total"))
        }
    }
}