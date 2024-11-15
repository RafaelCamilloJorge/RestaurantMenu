package com.unaerp.restaurantmenu.core.use_case.auth.impl

import com.unaerp.restaurantmenu.Domain.UserAuth
import com.unaerp.restaurantmenu.core.errors.GenericError
import com.unaerp.restaurantmenu.core.repositories.auth.AuthRepository
import com.unaerp.restaurantmenu.core.repositories.user_data.UserDataRepository
import com.unaerp.restaurantmenu.core.results.OnResult
import com.unaerp.restaurantmenu.core.use_case.auth.AuthUseCase

class AuthUseCaseImpl(
    private var authRepository: AuthRepository, private var userDataRepository: UserDataRepository
) : AuthUseCase {

    override suspend fun createAccountWithEmailAndPassword(
        email: String, password: String, name: String
    ): OnResult<Unit> {
        try {
            val response = authRepository.createAccountWithEmailAndPassword(email, password)

            if (response is OnResult.Success) {
                val responseCreateUser = userDataRepository.createUser(response.data, name)
                if (responseCreateUser is OnResult.Success) {
                    return OnResult.Success(Unit)
                }
                if (responseCreateUser is OnResult.Error) {
                    return OnResult.Error(responseCreateUser.exception)
                }
            }
            if (response is OnResult.Error) {
                return OnResult.Error(response.exception)
            }
        } catch (error: Exception) {
            return OnResult.Error(GenericError(error.message))
        }
        return OnResult.Error(GenericError("Erro ao tentar criar a conta"))
    }

    override suspend fun loginWithEmailAndPassword(
        email: String, password: String
    ): OnResult<UserAuth> {
        return authRepository.loginWithEmailAndPassword(email, password)
    }

    override suspend fun getTokenLogged(): OnResult<String> {
        return authRepository.getTokenUser()
    }

    override suspend fun logout(): OnResult<Unit> {
        return authRepository.logout()
    }

    override suspend fun forgotPassword(email: String): OnResult<Unit> {
        return authRepository.forgotPassword(email)
    }


}