package com.example.loginlourdes.ui.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.loginlourdes.ui.accounts.AccountScreen
import com.example.loginlourdes.ui.accounts.AccountsListViewModel
import kotlinx.coroutines.delay
@Composable
fun LoadingScreen(navController: NavController) {
    // Usamos LaunchedEffect para simular el retraso de 5 segundos
    LaunchedEffect(true) {
        delay(2000)  // Espera 5 segundos
        navController.navigate("accounts_screen")  // Navega a la pantalla AccountScreen después de 5 segundos
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 4.dp,
            strokeCap = StrokeCap.Round
        )
    }
}

@Composable
fun LoadingScreen() {
    // Usamos LaunchedEffect para simular el retraso de 5 segundos
    LaunchedEffect(true) {
        delay(2000)  // Espera 5 segundos
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 4.dp,
            strokeCap = StrokeCap.Round
        )
    }
}
