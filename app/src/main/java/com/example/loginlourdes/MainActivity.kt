package com.example.loginlourdes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.loginlourdes.navigation.accountGraph
import com.example.loginlourdes.navigation.loginGraph
import com.example.loginlourdes.Base.ui.theme.LoginLourdesTheme
import com.example.loginlourdes.main.MainScreen
import com.example.loginlourdes.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            LoginLourdesTheme {
                MainScreen(navController)
            }
        }
    }
}
