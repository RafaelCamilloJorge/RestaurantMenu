package com.unaerp.restaurantmenu.core.data_source.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.unaerp.restaurantmenu.Domain.MenuCategory
import com.unaerp.restaurantmenu.core.data_source.RemoteMenuDataSource
import com.unaerp.restaurantmenu.core.errors.GenericError
import com.unaerp.restaurantmenu.core.results.OnResult
import kotlinx.coroutines.tasks.await

class RemoteMenuDataSourceImpl(private val db: FirebaseFirestore) : RemoteMenuDataSource {
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
}