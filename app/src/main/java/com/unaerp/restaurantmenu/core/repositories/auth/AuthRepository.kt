package com.unaerp.restaurantmenu.core.repositories.auth

import com.unaerp.restaurantmenu.core.results.OnResult

interface AuthRepository {
    suspend fun createAccountWithEmailAndPassword(
        email: String,
        password: String
    ): OnResult<Boolean>

    suspend fun loginWithEmailAndPassword(email: String, password: String): OnResult<Boolean>

    suspend fun logout(): OnResult<Unit>

    suspend fun forgotPassword(email: String): OnResult<Unit>
}