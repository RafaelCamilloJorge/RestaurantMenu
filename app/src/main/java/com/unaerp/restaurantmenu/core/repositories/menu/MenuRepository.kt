package com.unaerp.restaurantmenu.core.repositories.menu

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.unaerp.restaurantmenu.Domain.MenuCategory
import com.unaerp.restaurantmenu.Domain.MenuItem
import com.unaerp.restaurantmenu.core.repositories.errors.GenericError
import com.unaerp.restaurantmenu.core.results.OnResult

interface MenuRepository {
    val db: FirebaseFirestore

    suspend fun getMenu(): OnResult<List<MenuCategory>> {
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

    suspend fun addItemInShoppingCar(item: MenuItem): OnResult<Unit> {
        try {
            val docRef: DocumentReference = db.collection("users").document("id do user")

//            var currentItemsInShoppingBag = emptyList<>()

//            var currentItems = docRef.get().addOnSuccessListener { result ->
//                currentItemsInShoppingBag = result as? Map<String, Any>?: emptyMap()
//            }


            docRef.update("totalvalue", "")
        } catch (error: Error) {

        }
        return OnResult.Success(Unit);
    }
}