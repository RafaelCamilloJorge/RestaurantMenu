package com.unaerp.restaurantmenu.core.use_case.menu.impl

import com.unaerp.restaurantmenu.Domain.ResponseMenuItem
import com.unaerp.restaurantmenu.core.repositories.menu.MenuRepository
import com.unaerp.restaurantmenu.core.results.OnResult
import com.unaerp.restaurantmenu.core.use_case.menu.MenuUseCase

class MenuUseCaseImpl(private val menuRepository: MenuRepository) : MenuUseCase {
    override suspend fun getMenu(): OnResult<Map<String, List<ResponseMenuItem>>> {
        return menuRepository.getMenu()
    }
}