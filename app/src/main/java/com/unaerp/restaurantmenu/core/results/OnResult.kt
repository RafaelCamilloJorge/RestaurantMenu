package com.unaerp.restaurantmenu.core.results

import com.unaerp.restaurantmenu.core.repositories.errors.GenericError

sealed class OnResult<T> {
    data class Success<T>(val data: T) : OnResult<T>()
    data class Error<T>(val exception: GenericError) : OnResult<T>()

    fun <R> fold(
        onSuccess: (T) -> R,
        onError: (GenericError) -> R
    ): R {
        return when (this) {
            is Success -> onSuccess(data)
            is Error -> onError(exception)
        }
    }
}

