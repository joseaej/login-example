package com.example.loginlourdes.main

import com.example.loginlourdes.navigation.AccountGraph
import com.example.loginlourdes.navigation.LoginGraph

data class MainState(
    val activeAccount:Boolean = false
){
    fun startDestination():String{
       return if (activeAccount) {
           AccountGraph.ROUTE
       } else {
           LoginGraph.ROUTE
       }
    }
}
