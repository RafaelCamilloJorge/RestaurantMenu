package com.unaerp.restaurantmenu.core.use_case.order.impl

import com.unaerp.restaurantmenu.Domain.Cart
import com.unaerp.restaurantmenu.Domain.CartItem
import com.unaerp.restaurantmenu.Domain.ResponseMenuItem
import com.unaerp.restaurantmenu.core.errors.GenericError
import com.unaerp.restaurantmenu.core.repositories.auth.AuthRepository
import com.unaerp.restaurantmenu.core.repositories.menu.MenuRepository
import com.unaerp.restaurantmenu.core.repositories.order.OrderRepository
import com.unaerp.restaurantmenu.core.repositories.user_data.UserDataRepository
import com.unaerp.restaurantmenu.core.results.OnResult
import com.unaerp.restaurantmenu.core.use_case.order.OrderUseCase

class OrderUseCaseImpl(
    private var authRepository: AuthRepository,
    private var orderRepository: OrderRepository,
    private var userDataRepository: UserDataRepository,
    private var menuRepository: MenuRepository,
) : OrderUseCase {
    override suspend fun itemInShoppingCar(
        item: ResponseMenuItem, quantity: Long
    ): OnResult<Unit> {
        try {
            val responseGetUserToken = authRepository.getTokenUser()

            if (responseGetUserToken is OnResult.Error) return OnResult.Error(responseGetUserToken.exception)

            if (responseGetUserToken is OnResult.Success) {
                val updatedQuantity: Long = quantity.toLong()
                val responseAddItem = userDataRepository.setQuantityProduct(
                    responseGetUserToken.data, item.id, updatedQuantity
                )
                if (responseAddItem is OnResult.Success) {
                    return OnResult.Success(responseAddItem.data)
                }
                if (responseAddItem is OnResult.Error) {
                    return OnResult.Error(responseAddItem.exception)
                }
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

    override suspend fun getMyCart(): OnResult<Cart> {
        try {
            val cart: Cart = Cart.initialCart()

            val responseGetMyToken = authRepository.getTokenUser()

            if (responseGetMyToken is OnResult.Error) return OnResult.Error(responseGetMyToken.exception)

            if (responseGetMyToken is OnResult.Success) {
                val responseCartUser = userDataRepository.getCardUser(responseGetMyToken.data)
                val responseMenu = menuRepository.getMenu()

                if (responseCartUser is OnResult.Error) return OnResult.Error(responseCartUser.exception)

                if (responseMenu is OnResult.Error) return OnResult.Error(responseMenu.exception)

                if (responseMenu is OnResult.Success && responseCartUser is OnResult.Success) {
                    for ((category, menuItems) in responseMenu.data) {
                        println("Categoria: $category")
                        for (menuItem in menuItems) {
                            responseCartUser.data.forEach { itemCart ->
                                if (itemCart.id == menuItem.id) {
                                    cart.items.add(
                                        CartItem(
                                            menuItem.id,
                                            menuItem.name,
                                            menuItem.description,
                                            menuItem.price,
                                            menuItem.image,
                                            menuItem.type,
                                            itemCart.quantity
                                        )
                                    )
                                    cart.addValue(itemCart.quantity * menuItem.price)
                                    println(itemCart.quantity * menuItem.price)
                                }
                            }
                            println("- ${menuItem.name}: R$ ${menuItem.price}")
                        }
                    }
                }
            }
            return OnResult.Success(cart)
        } catch (error: Exception) {
            return OnResult.Error(GenericError(error.message))
        }
    }

    override suspend fun finishPurchase(): OnResult<Unit> {
        try {
            val responseGetMyToken = authRepository.getTokenUser()

            if (responseGetMyToken is OnResult.Error) return OnResult.Error(responseGetMyToken.exception)

            if (responseGetMyToken is OnResult.Success) {
                val responseFinishPurchase =
                    userDataRepository.finishPurchase(responseGetMyToken.data)

                if (responseFinishPurchase is OnResult.Error) {
                    return OnResult.Error(responseFinishPurchase.exception)
                }
                if (responseFinishPurchase is OnResult.Success) {
                    return OnResult.Success(Unit)
                }
            }
            return OnResult.Error(GenericError("Erro ao finalizar pedido"))
        } catch (error: Exception) {
            return OnResult.Error(GenericError(error.message))
        }
    }
}