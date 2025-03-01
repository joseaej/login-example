package com.example.loginlourdes.Base.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.loginlourdes.R

/**
 * No data screen
 * Ventana genérica para los listados que no tienen datos
 *
 * Va en components
 */
@Composable
fun NoDataScreen() {
    Text(
        text = stringResource(id = R.string.no_data_messaje),
    )
}

@Preview
@Composable
fun PreviewNoDataScreen() {
    NoDataScreen()
}
