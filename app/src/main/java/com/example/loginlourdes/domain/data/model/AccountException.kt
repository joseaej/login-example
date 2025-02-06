package com.example.loginlourdes.domain.data.model

/**
 * Account exception, contiene todas las posibles errores que se puedan
 * encontrar en el caso de uso de Login y SignUp
 *
 * @constructor
 *
 * @param message
 */
sealed class AccountException(message:String): Exception(message) {


    data class TakenEmail(var email:String):AccountException("Ya existe este email")
    data object NoExistAccount:AccountException("La cuenta no existe")
    data object InvalidParam:AccountException("Email o contraseña invalidos")
}