package com.example.loginlourdes.ui.network

import java.lang.Exception

sealed class BaseResultList<T> {
    data class Success<T>(var data: ArrayList<T>?) : BaseResultList<T>()
    data class Error(var exception: Exception) : BaseResultList<Nothing>()
}