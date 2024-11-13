package com.unaerp.restaurantmenu.core.repositories.menu.impl

import com.unaerp.restaurantmenu.Domain.MenuCategory
import com.unaerp.restaurantmenu.Domain.MenuItem
import com.unaerp.restaurantmenu.core.data_source.RemoteAuthenticationDataSource
import com.unaerp.restaurantmenu.core.data_source.RemoteMenuDataSource
import com.unaerp.restaurantmenu.core.errors.GenericError
import com.unaerp.restaurantmenu.core.repositories.menu.MenuRepository
import com.unaerp.restaurantmenu.core.results.OnResult

class MenuRepositoryImpl(private var remoteMenuDataSource: RemoteMenuDataSource) : MenuRepository {
    override suspend fun getMenu(): OnResult<List<MenuCategory>> {
        return remoteMenuDataSource.getMenu()
    }
}