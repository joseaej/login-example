package com.example.loginlourdes


import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.loginlourdes.Base.ui.NoDataScreen
import com.example.loginlourdes.Base.ui.OfflineScreen
import com.example.loginlourdes.Login.AccountRegisterState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignUpScreenPasswordErrorTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Composable
    @Test
    fun invalid_password_true(){
        val password by remember { mutableStateOf("123") }
        val isPasswordError by remember { mutableStateOf(false) }
        val passwordErrortext by remember { mutableStateOf("") }
        composeTestRule.activity.setContent {
            val fakeSignUpState =  AccountRegisterState(
                password = password,
                isPasswordError = isPasswordError,
                passwordErrorFormat = passwordErrortext
            )
            SignUpScreenContent(events = SinginEvents(
                onPasswordChange = {},
                onEmailChange = {},
                onDateChange = {},
                onUserChange = {},
                onRegisterClick = {},
                onLoginClick = ::backToLogin
            ), state = fakeSignUpState)
        }
        //se prueba la escritura del componente
        composeTestRule.onNodeWithTag("passwordField").performTextInput("124")
        // se espera gasta que compose haga la recomposicion de la UI
        composeTestRule.waitForIdle()
        // se verifica
        composeTestRule.onNodeWithText(passwordErrortext).assertIsDisplayed()
    }
    private fun backToLogin(string: String, string2: String):Unit{

    }
}