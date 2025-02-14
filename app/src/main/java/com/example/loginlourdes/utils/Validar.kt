package com.example.loginlourdes.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun validaEmail(email:String):Boolean{
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    val regex = Regex(emailPattern)
    return regex.matches(email)
}
fun validarPassword(password:String):Boolean{
    return (password.length>=8)
}
fun validarDate(datePassword: String): Boolean {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    dateFormat.isLenient = false

    return try {
        val date = dateFormat.parse(datePassword)
        date.before(Date()) || date.equals(Date())
    } catch (e: Exception) {
        false
    }
}