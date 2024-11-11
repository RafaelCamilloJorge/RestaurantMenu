package com.unaerp.restaurantmenu.core.data_source

import com.unaerp.restaurantmenu.Domain.UserCreated
import com.unaerp.restaurantmenu.core.results.OnResult

interface FirebaseAuthenticationDataSource {
    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): OnResult<UserCreated>

    fun getTokenUser(): OnResult<String>

    suspend fun logout(): OnResult<Unit>

    suspend fun forgotPassword(email: String): OnResult<Unit>
}