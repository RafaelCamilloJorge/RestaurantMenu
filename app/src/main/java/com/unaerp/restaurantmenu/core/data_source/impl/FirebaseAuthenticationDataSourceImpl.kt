package com.unaerp.restaurantmenu.core.data_source.impl

import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.unaerp.restaurantmenu.Domain.UserCreated
import com.unaerp.restaurantmenu.core.data_source.FirebaseAuthenticationDataSource
import com.unaerp.restaurantmenu.core.repositories.errors.GenericError
import com.unaerp.restaurantmenu.core.results.OnResult
import kotlinx.coroutines.tasks.await

class FirebaseAuthenticationDataSourceImpl : FirebaseAuthenticationDataSource {
    private val auth: FirebaseAuth = Firebase.auth

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): OnResult<UserCreated> {
        try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser: FirebaseUser? = result.user
            return if (firebaseUser != null && firebaseUser.email != null) {
                OnResult.Success(
                    UserCreated(
                        firebaseUser.email!!,
                        firebaseUser.uid
                    )
                )
            } else {
                OnResult.Error(GenericError("Erro ao pegar dados"))
            }
        } catch (e: FirebaseException) {
            return OnResult.Error(GenericError(e.message))
        }
    }


    fun login(email: String, password: String): OnResult<Unit> {
        val response = auth.signInWithEmailAndPassword(email, password)
        if (response.isSuccessful) {
            return OnResult.Success(Unit)
        } else if (response.isCanceled) {
            return OnResult.Error(GenericError("Operação cancelada"))
        } else {
            return OnResult.Error(GenericError(response.exception?.message))
        }
    }

    override fun getTokenUser(): OnResult<String> {
        try {
            val user: FirebaseUser? = auth.currentUser
            val uid: String? = user?.uid
            return OnResult.Success(uid)
        } catch (e: FirebaseException) {
            return OnResult.Error(GenericError(e.message))
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
        try {
            auth.sendPasswordResetEmail(email)
            return OnResult.Success(Unit)
        } catch (error: FirebaseException) {
            return OnResult.Error(GenericError(error.message))
        }
    }
}