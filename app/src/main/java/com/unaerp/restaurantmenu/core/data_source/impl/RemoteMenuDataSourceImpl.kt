package com.unaerp.restaurantmenu.core.data_source.impl

import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.unaerp.restaurantmenu.Domain.ResponseMenuItem
import com.unaerp.restaurantmenu.core.data_source.RemoteMenuDataSource
import com.unaerp.restaurantmenu.core.errors.GenericError
import com.unaerp.restaurantmenu.core.results.OnResult
import kotlinx.coroutines.tasks.await

class RemoteMenuDataSourceImpl(private val db: FirebaseFirestore) : RemoteMenuDataSource {
    override suspend fun getMenu(): OnResult<Map<String, List<ResponseMenuItem>>> {
        //retornar um Map que vai ter a chave como tipo do produto e o value é uma lista do produto
        try {
            val listMenu: MutableList<ResponseMenuItem> = mutableListOf()
            val allProducts = db.collection("product").get().await()
            for (doc in allProducts) {
                listMenu.add(ResponseMenuItem.fromCollection(doc))
            }

            val mapTypeItems: MutableMap<String, MutableList<ResponseMenuItem>> = mutableMapOf()

            listMenu.forEach {
                if (mapTypeItems.containsKey(it.type)) {
                    val listType: MutableList<ResponseMenuItem>? = mapTypeItems[it.type]

                    listType?.add(it)
                    if (listType != null) {
                        mapTypeItems[it.type] = listType
                    }
                } else {
                    mapTypeItems[it.type] = mutableListOf(it)
                }
            }

            return OnResult.Success(mapTypeItems)
        } catch (error: FirebaseFirestoreException) {
            return OnResult.Error(GenericError(error.message))
        } catch (error: Exception) {
            return OnResult.Error(GenericError(error.message))
        } catch (error: FirebaseException) {
            return OnResult.Error(GenericError(error.message))
        }
    }
}