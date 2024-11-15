import com.unaerp.restaurantmenu.Domain.UserAuth
import com.unaerp.restaurantmenu.core.data_source.RemoteAuthenticationDataSource
import com.unaerp.restaurantmenu.core.repositories.auth.AuthRepository
import com.unaerp.restaurantmenu.core.results.OnResult

class AuthRepositoryImpl(private var remoteAuthenticationDataSourceImpl: RemoteAuthenticationDataSource) :
    AuthRepository {

    override suspend fun createAccountWithEmailAndPassword(
        email: String, password: String
    ): OnResult<UserAuth> {
        return remoteAuthenticationDataSourceImpl.createUserWithEmailAndPassword(email, password)
    }

    override suspend fun loginWithEmailAndPassword(
        email: String, password: String
    ): OnResult<UserAuth> {
        return remoteAuthenticationDataSourceImpl.loginWithEmailAndPassword(email, password)
    }

    override suspend fun logout(): OnResult<Unit> {
        return remoteAuthenticationDataSourceImpl.logout()
    }

    override suspend fun forgotPassword(email: String): OnResult<Unit> {
        return remoteAuthenticationDataSourceImpl.forgotPassword(email)
    }

    override fun getTokenUser(): OnResult<String> {
        return remoteAuthenticationDataSourceImpl.getTokenUser()
    }
}