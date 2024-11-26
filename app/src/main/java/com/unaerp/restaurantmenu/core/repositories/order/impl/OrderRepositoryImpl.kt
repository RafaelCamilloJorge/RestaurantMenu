package com.unaerp.restaurantmenu.core.repositories.order.impl

import com.unaerp.restaurantmenu.core.data_source.RemoteOrderDataSource
import com.unaerp.restaurantmenu.core.repositories.order.OrderRepository
import com.unaerp.restaurantmenu.core.results.OnResult

class OrderRepositoryImpl(private var remoteOrderDataSource: RemoteOrderDataSource) :
    OrderRepository {
    override suspend fun removeItemInShoppingCar(idItem: String, idUser: String): OnResult<Unit> {
        return remoteOrderDataSource.removeItemInShoppingCar(idItem, idUser)
    }

    override suspend fun getTotalValueOfShoppingCar(idUser: String): OnResult<Double> {
        return remoteOrderDataSource.getTotalValueOfShoppingCar(idUser)
    }
}