package com.example.loginlourdes.Base.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

/**
 * No data screen
 * Ventana genérica para los listados que no tienen datos
 *
 * Va en components
 */
@Composable
fun NoDataScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        Text(
            text = "No hay datos disponibles",
            fontSize = 20.sp
        )
    }
}

@Preview
@Composable
fun PreviewNoDataScreen() {
    NoDataScreen()
}
