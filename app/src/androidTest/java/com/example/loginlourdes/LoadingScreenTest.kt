package com.example.loginlourdes

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.loginlourdes.Base.ui.loading.LoadingScreen
import com.example.loginlourdes.Base.ui.theme.LoginLourdesTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoadingScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun muestra_el_indicador_de_carga() {
        composeTestRule.setContent {
            LoginLourdesTheme {
                LoadingScreen()
            }
        }

        // Verifica que el CircularProgressIndicator esté presente
        composeTestRule.onNodeWithTag("loading_indicator").assertExists()
    }

    @Test
    fun navega_a_la_pantalla_de_cuentas_tras_el_retraso() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            LoadingScreen(navController)
        }

        // Esperar más de 2 segundos (el tiempo que tarda en cambiar de pantalla)
        composeTestRule.mainClock.advanceTimeBy(2500)

        // Verifica que la pantalla de cuentas haya sido navegada
        composeTestRule.onNodeWithText("accounts_screen").assertExists()
    }
}