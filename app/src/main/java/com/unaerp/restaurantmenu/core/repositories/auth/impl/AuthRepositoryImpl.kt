import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.unaerp.restaurantmenu.Domain.MenuItem
import com.unaerp.restaurantmenu.core.repositories.auth.AuthRepository
import com.unaerp.restaurantmenu.core.repositories.errors.GenericError
import com.unaerp.restaurantmenu.core.results.OnResult
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl : AuthRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun createAccountWithEmailAndPassword(
        email: String,
        password: String
    ): OnResult<Boolean> {
        try {
            val authResult: Task<AuthResult> = auth.createUserWithEmailAndPassword(email, password)
            if (authResult.exception != null) {
                return OnResult.Error(GenericError(authResult.exception!!.message))
            } else {
                if (authResult.result.user?.uid != null) {
                    return OnResult.Error(GenericError("Erro ao criar conta"))
                }

                val mapUser = mutableMapOf<String, Any>()
                val user = authResult.result.user!!

                mapUser.put("id", user.uid)
                user.email?.let { mapUser.put("email", it) }
                mapUser.put("shopping_cart", emptyList<Map<String, Any>>())

                FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(authResult.result.user!!.uid)
                    .set(mapUser)
            }
            return OnResult.Success(authResult.isSuccessful)
        } catch (error: Error) {
            return OnResult.Error(GenericError("Erro ao criar a conta, tente novamente"))
        }
    }

    override suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): OnResult<Boolean> {
        try {
            val authResult: Task<AuthResult> = auth.signInWithEmailAndPassword(email, password)
            if (authResult.exception != null) return OnResult.Error(GenericError(authResult.exception!!.message))
            return OnResult.Success(authResult.isSuccessful)
        } catch (error: Error) {
            return OnResult.Error(GenericError("Erro ao entrar, tente novamente"))
        }
    }

    override suspend fun logout(): OnResult<Unit> {
        try {
            auth.signOut()
            return OnResult.Success(Unit)
        } catch (error: Error) {
            return OnResult.Error(GenericError("Erro ao sair, tente novamente"))
        }
    }

    override suspend fun forgotPassword(email: String): OnResult<Unit> {
        try {
            auth.sendPasswordResetEmail(email)
            return OnResult.Success(Unit)
        } catch (error: Error) {
            return OnResult.Error(GenericError("Erro ao enviar email de recuperação"))
        }
    }
}