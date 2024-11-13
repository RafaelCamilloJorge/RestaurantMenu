package com.unaerp.restaurantmenu.core.repositories.auth

import com.unaerp.restaurantmenu.Domain.UserAuth
import com.unaerp.restaurantmenu.core.results.OnResult

interface AuthRepository {
    suspend fun createAccountWithEmailAndPassword(
        email: String, password: String
    ): OnResult<UserAuth>

    suspend fun loginWithEmailAndPassword(email: String, password: String): OnResult<UserAuth>

    suspend fun logout(): OnResult<Unit>

    suspend fun forgotPassword(email: String): OnResult<Unit>

    fun getTokenUser(): OnResult<String>
}