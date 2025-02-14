package com.example.loginlourdes.utils

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.example.loginlourdes.domain.data.model.account.Account
import kotlinx.coroutines.launch

@Composable
fun SnackbarAccount(account: Account?, snackbarHostState: SnackbarHostState) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(account) {
        account?.let {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = "Cuenta: ${it.email} - Contraseña: ${it.password}",
                    actionLabel = "Cerrar",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
}
