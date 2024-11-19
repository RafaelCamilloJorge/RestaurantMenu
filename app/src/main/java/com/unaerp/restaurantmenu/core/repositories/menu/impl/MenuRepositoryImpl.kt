package com.unaerp.restaurantmenu.core.repositories.menu.impl

import com.unaerp.restaurantmenu.Domain.ResponseMenuItem
import com.unaerp.restaurantmenu.core.data_source.RemoteMenuDataSource
import com.unaerp.restaurantmenu.core.repositories.menu.MenuRepository
import com.unaerp.restaurantmenu.core.results.OnResult

class MenuRepositoryImpl(private var remoteMenuDataSource: RemoteMenuDataSource) : MenuRepository {
    override suspend fun getMenu(): OnResult<Map<String, List<ResponseMenuItem>>> {
        return remoteMenuDataSource.getMenu()
    }
}