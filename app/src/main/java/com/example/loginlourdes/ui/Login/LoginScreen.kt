package com.example.loginlourdes.ui.theme.Login


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.loginlourdes.Utils.ErrorModal

data class LoginEvents(
    val onEmailChange: (String) -> Unit,
    val onPasswordChange: (String) -> Unit,
    val onLoginClick: () -> Unit,
    val onRegisterClick: () -> Unit
)
@Composable
fun LoginScreen(loginViewModel: LoginViewModel,email:String,password:String, goToRegister: () -> Unit, goToAccounts: () -> Unit) {

    LaunchedEffect(email,password) {
        loginViewModel.initfields(email, password)
    }
    LoginScreenContent(
        events = LoginEvents(
            onEmailChange = loginViewModel::onEmailChange,
            onPasswordChange = loginViewModel::onPasswordChange,
            onLoginClick = loginViewModel::onLoginClick,
            onRegisterClick = goToRegister
        ),
        state = LoginState(
            email = email,
            password = password,
            isEmailError = loginViewModel.state.isEmailError,
            emailErrorFormat = loginViewModel.state.emailErrorFormat
        )
    )
    if (loginViewModel.state.success) {
        goToAccounts()
    }
}

@Composable
private fun LoginScreenContent(
    modifier: Modifier = Modifier,
    state: LoginState,
    events: LoginEvents
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
        ) {
            Text(
                text = "Iniciar Sesión",
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Column {
                TextField(
                    value = state.email,
                    onValueChange = events.onEmailChange,
                    label = { Text("Email") },
                    isError = state.isEmailError,
                    leadingIcon = { LeadingEmailIcon() }
                )
                if (state.isEmailError) {
                    Text(text = state.emailErrorFormat, color = Color.Red)
                }
            }
            FormFieldPassword(
                value = state.password,
                events = events,
                label = { Text("Contraseña") },
                leadingIcon = { LeadingPassIcon() }
            )

            Button(onClick = events.onLoginClick) {
                Text("Iniciar Sesión")
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("¿No tienes una cuenta?")
                TextButton(onClick = events.onRegisterClick) {
                    Text("Registrarte")
                }
            }
        }
    }
}

@Composable
fun FormFieldPassword(value: String, events: LoginEvents, label: @Composable () -> Unit, leadingIcon: @Composable () -> Unit) {
    TextField(
        value = value,
        onValueChange = events.onPasswordChange,
        label = label,
        leadingIcon = leadingIcon
    )
}

@Composable
fun SignInScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Pantalla de Registro", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { navController.popBackStack() }) {
                Text("Regresar a Inicio de Sesión")
            }
        }
    }
}

@Composable
fun LeadingEmailIcon() {
    Icon(
        imageVector = Icons.Filled.Email,
        contentDescription = "Email Icon",
        modifier = Modifier.size(24.dp),
        tint = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun LeadingPassIcon() {
    Icon(
        imageVector = Icons.Filled.Lock,
        contentDescription = "Password Icon",
        modifier = Modifier.size(24.dp),
        tint = MaterialTheme.colorScheme.primary
    )
}
