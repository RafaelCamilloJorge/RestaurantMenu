package com.unaerp.restaurantmenu.core.repositories.user_data

import com.unaerp.restaurantmenu.Domain.MenuItem
import com.unaerp.restaurantmenu.Domain.UserAuth
import com.unaerp.restaurantmenu.core.results.OnResult

interface UserDataRepository {
    suspend fun createUser(userAuth: UserAuth, name: String): OnResult<Unit>
}