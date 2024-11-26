package com.unaerp.restaurantmenu.core.data_source.impl

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.unaerp.restaurantmenu.Domain.UserAuth
import com.unaerp.restaurantmenu.core.data_source.RemoteAuthenticationDataSource
import com.unaerp.restaurantmenu.core.errors.GenericError
import com.unaerp.restaurantmenu.core.results.OnResult
import kotlinx.coroutines.tasks.await

class RemoteAuthenticationDataSourceImpl(private val auth: FirebaseAuth) :
    RemoteAuthenticationDataSource {
    override suspend fun createUserWithEmailAndPassword(
        email: String, password: String
    ): OnResult<UserAuth> {
        try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser: FirebaseUser? = result.user
            return if (firebaseUser != null && firebaseUser.email != null) {
                OnResult.Success(
                    UserAuth(
                        firebaseUser.email!!, firebaseUser.uid
                    )
                )
            } else {
                OnResult.Error(GenericError("Erro ao pegar dados"))
            }
        } catch (e: FirebaseAuthException) {
            return OnResult.Error(GenericError(e.message))
        }
    }

    override suspend fun loginWithEmailAndPassword(
        email: String, password: String
    ): OnResult<UserAuth> {
        try {
            val response = auth.signInWithEmailAndPassword(email, password).await()

            if (response.user != null) {
                return OnResult.Success(
                    UserAuth(
                        response.user!!.email!!, response.user!!.uid
                    )
                )
            }
            return OnResult.Error(GenericError("Erro ao ler credenciais"))
        } catch (error: FirebaseAuthException) {
            return OnResult.Error(GenericError("Erro ao entrar, tente novamente"))
        }
    }

    override fun getTokenUser(): OnResult<String> {
        return try {
            val user: FirebaseUser? = auth.currentUser
            val uid = user?.uid
            if (uid != null) {
                OnResult.Success(uid)
            } else {
                OnResult.Error(GenericError("Erro ao pegar o identificador do usuário"))
            }
        } catch (e: FirebaseAuthException) {
            OnResult.Error(GenericError(e.message))
        }
    }

    override suspend fun logout(): OnResult<Unit> {
        try {
            auth.signOut()
            return OnResult.Success(Unit)
        } catch (error: FirebaseException) {
            return OnResult.Error(GenericError(error.message))
        }
    }

    override suspend fun forgotPassword(email: String): OnResult<Unit> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            OnResult.Success(Unit)
        } catch (error: Exception) {
            OnResult.Error(GenericError(error.message ?: "Erro ao enviar email"))
        }
    }


    override suspend fun checkUserIsLogged(): OnResult<Boolean> {
        return try {
            OnResult.Success(auth.currentUser != null)
        } catch (error: FirebaseException) {
            OnResult.Error(GenericError(error.message))
        }
    }
}