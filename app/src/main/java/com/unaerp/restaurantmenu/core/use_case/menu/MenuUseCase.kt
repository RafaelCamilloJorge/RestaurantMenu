package com.unaerp.restaurantmenu.core.use_case.menu

import com.unaerp.restaurantmenu.Domain.MenuCategory
import com.unaerp.restaurantmenu.Domain.MenuItem
import com.unaerp.restaurantmenu.core.results.OnResult

interface MenuUseCase {
    suspend fun getMenu(): OnResult<Map<String, List<MenuItem>>>
}