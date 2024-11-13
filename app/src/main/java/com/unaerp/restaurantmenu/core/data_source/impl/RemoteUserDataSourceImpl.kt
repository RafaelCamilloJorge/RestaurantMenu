package com.unaerp.restaurantmenu.core.data_source.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.unaerp.restaurantmenu.Domain.UserAuth
import com.unaerp.restaurantmenu.core.data_source.RemoteUserDataSource
import com.unaerp.restaurantmenu.core.errors.GenericError
import com.unaerp.restaurantmenu.core.results.OnResult

class RemoteUserDataSourceImpl(private var firestore: FirebaseFirestore) : RemoteUserDataSource {
    override suspend fun createUser(userAuth: UserAuth, name: String): OnResult<Unit> {
        return try {
            val mapUser = mutableMapOf<String, Any>()

            mapUser["id"] = userAuth.uid
            mapUser["name"] = name
            mapUser["email"] = userAuth.email
            mapUser["shopping_cart"] = emptyList<Map<String, Any>>()

            firestore.collection("users").document(userAuth.uid).set(mapUser)
            return OnResult.Success(Unit)
        } catch (error: FirebaseFirestoreException) {
            OnResult.Error(GenericError(error.message))
        }

    }
}