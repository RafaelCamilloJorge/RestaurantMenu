package com.unaerp.restaurantmenu.core.repositories.user_data.impl

import com.unaerp.restaurantmenu.Domain.UserAuth
import com.unaerp.restaurantmenu.core.data_source.impl.RemoteUserDataSourceImpl
import com.unaerp.restaurantmenu.core.repositories.user_data.UserDataRepository
import com.unaerp.restaurantmenu.core.results.OnResult

class UserDataRepositoryImpl(private var remoteUserDataSourceImpl: RemoteUserDataSourceImpl) :
    UserDataRepository {
    override suspend fun createUser(userAuth: UserAuth, name: String): OnResult<Unit> {
        return remoteUserDataSourceImpl.createUser(userAuth, name)
    }
}
