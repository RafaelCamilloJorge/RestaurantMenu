package com.unaerp.restaurantmenu.core.data_source.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.unaerp.restaurantmenu.Domain.MenuItem
import com.unaerp.restaurantmenu.core.data_source.RemoteMenuDataSource
import com.unaerp.restaurantmenu.core.errors.GenericError
import com.unaerp.restaurantmenu.core.results.OnResult
import kotlinx.coroutines.tasks.await

class RemoteMenuDataSourceImpl(private val db: FirebaseFirestore) : RemoteMenuDataSource {
    override suspend fun getMenu(): OnResult<Map<String, List<MenuItem>>> {
        //retornar um Map que vai ter a chave como tipo do produto e o value é uma lista do produto
        try {
            val listMenu: MutableList<MenuItem> = mutableListOf()
            db.collection("product").get().addOnSuccessListener { result ->
                for (doc in result) {
                    listMenu.add(MenuItem.fromCollection(doc))
                }
            }.await()

            val mapTypeItems: MutableMap<String, MutableList<MenuItem>> = mutableMapOf()

            listMenu.forEach {
                if (mapTypeItems.containsKey(it.type)) {
                    val listType: MutableList<MenuItem>? = mapTypeItems[it.type]

                    listType?.add(it)
                    if (listType != null) {
                        mapTypeItems[it.type] = listType
                    }
                } else {
                    val listType: MutableList<MenuItem> = mutableListOf()
                    listType.add(it)
                    mapTypeItems[it.type] = listType
                }
            }


            return OnResult.Success(mapTypeItems)
        } catch (error: FirebaseFirestoreException) {
            return OnResult.Error(GenericError(error.message))
        }
    }
}