package com.unaerp.restaurantmenu.core.data_source

import com.unaerp.restaurantmenu.Domain.UserAuth
import com.unaerp.restaurantmenu.core.results.OnResult

interface RemoteUserDataSource {

    suspend fun createUser(userAuth: UserAuth, name: String): OnResult<Unit>
}