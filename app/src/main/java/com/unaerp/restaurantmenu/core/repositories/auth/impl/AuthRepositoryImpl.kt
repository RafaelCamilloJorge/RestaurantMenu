package com.unaerp.restaurantmenu.core.repositories.auth.impl

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.unaerp.restaurantmenu.core.repositories.auth.AuthRepository
import com.unaerp.restaurantmenu.core.repositories.errors.GenericError
import com.unaerp.restaurantmenu.core.results.OnResult

class AuthRepositoryImpl : AuthRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun createAccountWithEmailAndPassword(
        email: String,
        password: String
    ): OnResult<Boolean> {
        try {
            val authResult: Task<AuthResult> = auth.createUserWithEmailAndPassword(email, password)
            if (authResult.exception != null) return OnResult.Error(GenericError(authResult.exception!!.message))
            return OnResult.Success(authResult.isSuccessful)
        } catch (error: Error) {
            return OnResult.Error(GenericError("Erro ao criar a conta, tente novamente"))
        }
    }

    override fun loginWithEmailAndPassword(email: String, password: String): OnResult<Boolean> {
        try {
            val authResult: Task<AuthResult> = auth.signInWithEmailAndPassword(email, password)
            if (authResult.exception != null) return OnResult.Error(GenericError(authResult.exception!!.message))
            return OnResult.Success(authResult.isSuccessful)
        } catch (error: Error) {
            return OnResult.Error(GenericError("Erro ao entrar, tente novamente"))
        }
    }

    override fun logout(): OnResult<Boolean> {
        try {
            auth.signOut()
            return OnResult.Success(true)
        } catch (error: Error) {
            return OnResult.Error(GenericError("Erro ao sair, tente novamente"))
        }
    }

    override fun forgotPassword(email: String): OnResult<Boolean> {
        try {
            auth.sendPasswordResetEmail(email)
            return OnResult.Success(true)
        } catch (error: Error) {
            return OnResult.Error(GenericError("Erro ao enviar email de recuperação"))
        }
    }
}