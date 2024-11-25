package com.unaerp.restaurantmenu.core.use_case.auth

import com.unaerp.restaurantmenu.Domain.UserAuth
import com.unaerp.restaurantmenu.core.results.OnResult

interface AuthUseCase {
    suspend fun createAccountWithEmailAndPassword(
        email: String, password: String, name: String
    ): OnResult<Unit>

    suspend fun loginWithEmailAndPassword(email: String, password: String): OnResult<UserAuth>

    suspend fun getTokenLogged(): OnResult<String>

    suspend fun logout(): OnResult<Unit>

    suspend fun forgotPassword(email: String): OnResult<Unit>

    suspend fun checkUserIsLogged(): OnResult<Boolean>
}