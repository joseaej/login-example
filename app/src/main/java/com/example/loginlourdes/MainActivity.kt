package com.example.loginlourdes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.loginlourdes.navigation.AccountGraph
import com.example.loginlourdes.navigation.LoginGraph
import com.example.loginlourdes.navigation.accountGraph
import com.example.loginlourdes.navigation.loginGraph
import com.example.loginlourdes.ui.theme.LoginLourdesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            val startDestination = if (viewModel.state.activeAccount) {
                AccountGraph.ROUTE
            } else {
                LoginGraph.ROUTE
            }

            LoginLourdesTheme {
                NavHost(
                    navController = navController,
                    startDestination = startDestination
                ) {
                    loginGraph(navController)
                    accountGraph(navController)
                }
            }
        }
    }
}
