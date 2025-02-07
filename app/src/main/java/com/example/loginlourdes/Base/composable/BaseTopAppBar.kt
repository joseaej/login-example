package com.example.loginlourdes.Base.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseTopAppBar(appBarState: BaseTopAppBarState) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = appBarState.tittle) },
        navigationIcon = {
            IconButton(onClick = appBarState.upAction) {
                Icon(imageVector = appBarState.icon, contentDescription = "Back")
            }
        },
        actions = {
            appBarState.acctions.filter { it.isVisible }.forEach { action ->
                IconButton(onClick = action.onCLick) {
                    Icon(
                        imageVector = action.icon,
                        contentDescription = action.contentDescription,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            if (appBarState.acctions.any { !it.isVisible }) {
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    appBarState.acctions.filter { !it.isVisible }.forEach { action ->
                        DropdownMenuItem(
                            text = { Text(action.nameTitle) },
                            onClick = {
                                expanded = false
                                action.onCLick()
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = action.icon,
                                    contentDescription = action.contentDescription
                                )
                            }
                        )
                    }
                }

                IconButton(onClick = { expanded = true }) {
                    Icon(imageVector = appBarState.icon, contentDescription = "More Options")
                }
            }
        }
    )
}

@Preview
@Composable
fun PreviewBaseTopAppBar() {
    // Aquí podrías agregar una vista previa con datos de ejemplo si defines iconos válidos
}