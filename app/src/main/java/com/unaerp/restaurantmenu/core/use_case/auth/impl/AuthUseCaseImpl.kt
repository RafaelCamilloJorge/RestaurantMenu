package com.unaerp.restaurantmenu.core.use_case.auth.impl

import DependencyInitializer
import com.unaerp.restaurantmenu.Domain.UserAuth
import com.unaerp.restaurantmenu.core.errors.GenericError
import com.unaerp.restaurantmenu.core.repositories.auth.AuthRepository
import com.unaerp.restaurantmenu.core.repositories.user_data.UserDataRepository
import com.unaerp.restaurantmenu.core.results.OnResult
import com.unaerp.restaurantmenu.core.use_case.auth.AuthUseCase

class AuthUseCaseImpl(
    private var authRepository: AuthRepository, private var userDataRepository: UserDataRepository
) : AuthUseCase {

    suspend fun createAccountWithEmailAndPassword(
        email: String, password: String, name: String
    ): OnResult<Unit> {
        esse é o caminho
                add o todos no [DependencyInitializer]
//        try {
//            val response = authRepository.createAccountWithEmailAndPassword(email, password);
//            if (response is OnResult.Success) {
//                return userDataRepository.createUser(response.data, name)
//            }
//
//        } catch (error: Exception) {
//            return OnResult.Error(GenericError(error.message))
//        }
    }
}