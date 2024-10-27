package com.unaerp.restaurantmenu.core.repositories.auth

import com.unaerp.restaurantmenu.core.results.OnResult

interface AuthRepository {
    fun createAccountWithEmailAndPassword(email: String, password: String): OnResult<Boolean>

    fun loginWithEmailAndPassword(email: String, password: String): OnResult<Boolean>

    fun logout(): OnResult<Boolean>

    fun forgotPassword(email: String): OnResult<Boolean>
}