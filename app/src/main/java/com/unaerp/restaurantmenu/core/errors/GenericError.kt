package com.unaerp.restaurantmenu.core.errors

class GenericError (private var error: String?) : Exception(error) {
    fun messageError(): String {
        return error ?: this.message ?: "Erro desconhecido"
    }
}