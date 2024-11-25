package com.unaerp.restaurantmenu.Feature.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unaerp.restaurantmenu.Domain.MenuCategory
import com.unaerp.restaurantmenu.Domain.ResponseMenuItem
import com.unaerp.restaurantmenu.core.use_case.menu.MenuUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MenuViewModel(private var menuUseCaseImpl: MenuUseCase) : ViewModel() {

    private val _menuState = MutableStateFlow<Result<List<Any>>?>(null)
    val menuState = _menuState.asStateFlow()

    fun getMenu() {
        viewModelScope.launch {
            val result = menuUseCaseImpl.getMenu()
            result.fold(onSuccess = { map ->
                val flatList = mutableListOf<Any>()

                map.forEach { (category, items) ->
                    flatList.add(MenuCategory(0L, category, items))
                    flatList.addAll(items)
                }

                _menuState.value = Result.success(flatList)
            }, onError = { error ->
                _menuState.value = Result.failure(error)
            })
        }
    }
}