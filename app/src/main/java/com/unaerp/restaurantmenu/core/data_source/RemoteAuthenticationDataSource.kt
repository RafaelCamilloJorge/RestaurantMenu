package com.unaerp.restaurantmenu.core.data_source

import com.unaerp.restaurantmenu.Domain.UserAuth
import com.unaerp.restaurantmenu.core.results.OnResult

interface RemoteAuthenticationDataSource {
    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): OnResult<UserAuth>

    suspend fun loginWithEmailAndPassword(email: String, password: String): OnResult<UserAuth>

    fun getTokenUser(): OnResult<String>

    suspend fun logout(): OnResult<Unit>

    suspend fun forgotPassword(email: String): OnResult<Unit>

    suspend fun checkUserIsLogged(): OnResult<Boolean>
}