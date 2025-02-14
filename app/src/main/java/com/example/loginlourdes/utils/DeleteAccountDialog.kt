package com.example.loginlourdes.utils

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.loginlourdes.domain.data.model.account.Account

@Composable
fun DeleteAccountDialog(
    account: Account,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Eliminar cuenta") },
        text = { Text(text = "¿Está seguro de que desea eliminar la cuenta: ${account.email}?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancelar")
            }
        }
    )
}