package com.unaerp.restaurantmenu.core.repositories.menu

import com.unaerp.restaurantmenu.Domain.MenuItem
import com.unaerp.restaurantmenu.core.results.OnResult

interface MenuRepository {

    suspend fun getMenu(): OnResult<Map<String, List<MenuItem>>>
}