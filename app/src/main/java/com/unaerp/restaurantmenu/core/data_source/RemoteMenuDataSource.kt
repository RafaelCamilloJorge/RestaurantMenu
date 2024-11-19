package com.unaerp.restaurantmenu.core.data_source

import com.unaerp.restaurantmenu.Domain.ResponseMenuItem
import com.unaerp.restaurantmenu.core.results.OnResult

interface RemoteMenuDataSource {

    suspend fun getMenu(): OnResult<Map<String, List<ResponseMenuItem>>>

}