package com.example.loginlourdes.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.loginlourdes.navigation.accountGraph
import com.example.loginlourdes.navigation.loginGraph

@Composable
fun MainScreen(navController:NavHostController,viewModel: MainViewModel = hiltViewModel()) {

    LaunchedEffect (Unit){
       snapshotFlow{viewModel.state}.collect{
           state ->
           val destination = state.startDestination()
           if (navController.currentDestination?.route!=destination){
               navController.navigate(destination)
           }
       }
    }

    NavHost(
        navController = navController,
        startDestination = viewModel.state.startDestination()
    ) {
        loginGraph(navController)
        accountGraph(navController)
    }
}
