package com.unaerp.restaurantmenu.core.repositories.menu.impl

import com.unaerp.restaurantmenu.Domain.MenuCategory
import com.unaerp.restaurantmenu.Domain.MenuItem
import com.unaerp.restaurantmenu.core.data_source.FirebaseAuthenticationDataSource
import com.unaerp.restaurantmenu.core.data_source.FirestoreDataSource
import com.unaerp.restaurantmenu.core.repositories.errors.GenericError
import com.unaerp.restaurantmenu.core.repositories.menu.MenuRepository
import com.unaerp.restaurantmenu.core.results.OnResult

class MenuRepositoryImpl(
    private var firestoreDataSource: FirestoreDataSource,
    private var firebaseAuthenticationDataSource: FirebaseAuthenticationDataSource
) : MenuRepository {
    override suspend fun getMenu(): OnResult<List<MenuCategory>> {
        return firestoreDataSource.getMenu()
    }

    override suspend fun addItemInShoppingCar(item: MenuItem): OnResult<Unit> {
        try {
            var idUser: String? = null

            when (val responseGetTokenUser = firebaseAuthenticationDataSource.getTokenUser()) {
                is OnResult.Success -> idUser = responseGetTokenUser.data
                is OnResult.Error -> return OnResult.Error(responseGetTokenUser.exception)
            }

            return firestoreDataSource.addItemInShoppingCar(item, idUser!!)

        } catch (error: Error) {
            return OnResult.Error(GenericError("Erro ao adicionar item"))
        }
    }

    override suspend fun removeItemInShoppingCar(idItem: String): OnResult<Unit> {
        try {
            var idUser: String? = null

            when (val responseGetTokenUser = firebaseAuthenticationDataSource.getTokenUser()) {
                is OnResult.Success -> idUser = responseGetTokenUser.data
                is OnResult.Error -> return OnResult.Error(responseGetTokenUser.exception)
            }

            return firestoreDataSource.removeItemInShoppingCar(idItem, idUser!!)
        } catch (error: Error) {
            return OnResult.Error(GenericError("Erro ao remover item"))
        }
    }

    override suspend fun editQuantityOfItemInShoppingCar(
        idItem: String, newQuantity: Int
    ): OnResult<Unit> {
        try {
            var idUser: String? = null

            when (val responseGetTokenUser = firebaseAuthenticationDataSource.getTokenUser()) {
                is OnResult.Success -> idUser = responseGetTokenUser.data
                is OnResult.Error -> return OnResult.Error(responseGetTokenUser.exception)
            }

            return firestoreDataSource.editQuantityOfItemInShoppingCar(
                idItem, newQuantity, idUser!!
            )
        } catch (error: Error) {
            return OnResult.Error(GenericError("Erro ao editar item"))
        }
    }

    override suspend fun getTotalValueOfShoppingCar(
    ): OnResult<Double> {
        try {
            var idUser: String? = null

            when (val responseGetTokenUser = firebaseAuthenticationDataSource.getTokenUser()) {
                is OnResult.Success -> idUser = responseGetTokenUser.data
                is OnResult.Error -> return OnResult.Error(responseGetTokenUser.exception)
            }

            return firestoreDataSource.getTotalValueOfShoppingCar(idUser!!)
        } catch (error: Error) {
            return OnResult.Error(GenericError("Erro ao ao buscar o valor total"))
        }
    }
}