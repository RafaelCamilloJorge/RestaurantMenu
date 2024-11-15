package com.unaerp.restaurantmenu.core.use_case.order.impl

import com.unaerp.restaurantmenu.Domain.MenuItem
import com.unaerp.restaurantmenu.core.errors.GenericError
import com.unaerp.restaurantmenu.core.repositories.auth.AuthRepository
import com.unaerp.restaurantmenu.core.repositories.order.OrderRepository
import com.unaerp.restaurantmenu.core.results.OnResult
import com.unaerp.restaurantmenu.core.use_case.order.OrderUseCase

class OrderUseCaseImpl(
    private var authRepository: AuthRepository, private var orderRepository: OrderRepository
) : OrderUseCase {
    override suspend fun addItemInShoppingCar(item: MenuItem): OnResult<Unit> {
        try {
            val responseGetUserToken = authRepository.getTokenUser()

            if (responseGetUserToken is OnResult.Success) {
                return orderRepository.addItemInShoppingCar(item, responseGetUserToken.data)
            }
            if (responseGetUserToken is OnResult.Error) {
                return OnResult.Error(responseGetUserToken.exception)
            }

        } catch (error: Exception) {
            return OnResult.Error(GenericError(error.message))
        }
        return OnResult.Error(GenericError("Erro ao adicionar item ao carrinho"))
    }

    override suspend fun removeItemInShoppingCar(idItem: String): OnResult<Unit> {
        try {
            val responseGetUserToken = authRepository.getTokenUser()

            if (responseGetUserToken is OnResult.Success) {
                return orderRepository.removeItemInShoppingCar(idItem, responseGetUserToken.data)
            }
            if (responseGetUserToken is OnResult.Error) {
                return OnResult.Error(responseGetUserToken.exception)
            }

        } catch (error: Exception) {
            return OnResult.Error(GenericError(error.message))
        }
        return OnResult.Error(GenericError("Erro ao remover item ao carrinho"))
    }

    override suspend fun editQuantityOfItemInShoppingCar(
        idItem: String, newQuantity: Int
    ): OnResult<Unit> {
        try {
            val responseGetUserToken = authRepository.getTokenUser()

            if (responseGetUserToken is OnResult.Success) {
                return orderRepository.editQuantityOfItemInShoppingCar(
                    idItem, newQuantity, responseGetUserToken.data
                )
            }
            if (responseGetUserToken is OnResult.Error) {
                return OnResult.Error(responseGetUserToken.exception)
            }

        } catch (error: Exception) {
            return OnResult.Error(GenericError(error.message))
        }
        return OnResult.Error(GenericError("Erro ao adicionar item ao carrinho"))
    }

    override suspend fun getTotalValueOfShoppingCar(
    ): OnResult<Double> {
        try {
            val responseGetUserToken = authRepository.getTokenUser()

            if (responseGetUserToken is OnResult.Success) {
                return orderRepository.getTotalValueOfShoppingCar(
                    responseGetUserToken.data
                )
            }
            if (responseGetUserToken is OnResult.Error) {
                return OnResult.Error(responseGetUserToken.exception)
            }

        } catch (error: Exception) {
            return OnResult.Error(GenericError(error.message))
        }
        return OnResult.Error(GenericError("Erro ao pegar valor total do carrinho"))
    }
}