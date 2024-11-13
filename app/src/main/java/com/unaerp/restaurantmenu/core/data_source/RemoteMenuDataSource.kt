package com.unaerp.restaurantmenu.core.data_source

import com.unaerp.restaurantmenu.Domain.MenuCategory
import com.unaerp.restaurantmenu.core.results.OnResult

interface RemoteMenuDataSource {

    suspend fun getMenu(): OnResult<List<MenuCategory>>

}