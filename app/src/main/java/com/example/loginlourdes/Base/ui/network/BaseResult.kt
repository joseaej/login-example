package com.example.loginlourdes.Base.ui.network

import java.lang.Exception

sealed class BaseResult<out T> {
    data class Success<T>(var data: T) : BaseResult<T>()
    data class Error(var exception: Exception) : BaseResult<Nothing>()
}