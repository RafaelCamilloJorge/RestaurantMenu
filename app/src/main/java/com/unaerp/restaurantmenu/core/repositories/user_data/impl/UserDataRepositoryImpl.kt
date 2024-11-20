package com.unaerp.restaurantmenu.core.repositories.user_data.impl

import com.unaerp.restaurantmenu.Domain.ResponseShoppingCartItem
import com.unaerp.restaurantmenu.Domain.UserAuth
import com.unaerp.restaurantmenu.core.data_source.impl.RemoteUserDataSourceImpl
import com.unaerp.restaurantmenu.core.repositories.user_data.UserDataRepository
import com.unaerp.restaurantmenu.core.results.OnResult

class UserDataRepositoryImpl(private var remoteUserDataSourceImpl: RemoteUserDataSourceImpl) :
    UserDataRepository {
    override suspend fun createUser(userAuth: UserAuth, name: String): OnResult<Unit> {
        return remoteUserDataSourceImpl.createUser(userAuth, name)
    }

    override suspend fun getCardUser(id: String): OnResult<List<ResponseShoppingCartItem>> {
        return remoteUserDataSourceImpl.getCartUser(id)
    }

    override suspend fun finishPurchase(id: String): OnResult<Unit> {
        return remoteUserDataSourceImpl.finishPurchase(id)
    }

    override suspend fun setQuantityProduct(
        idUser: String,
        idProduct: String,
        quantity: Int
    ): OnResult<Unit> {
        return remoteUserDataSourceImpl.setProductCart(idUser, idProduct, quantity)
    }
}
