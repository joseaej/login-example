package com.example.loginlourdes

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginlourdes.Login.AccountRegisterState
import com.example.loginlourdes.ui.theme.Login.LeadingEmailIcon
import com.example.loginlourdes.ui.theme.Login.LeadingPassIcon
import com.example.loginlourdes.ui.Signin.SignInViewModel

data class SinginEvents(
    val onEmailChange: (String) -> Unit,
    val onPasswordChange: (String) -> Unit,
    val onUserChange:(String)->Unit,
    val onDateChange:(String)->Unit,
    val onLoginClick:(String,String)->Unit,
    val onRegisterClick: () -> Unit
)

@Composable
fun SignUpScreen(viewModel: SignInViewModel,backToLogin:(String,String)->Unit,goToAccounts:()->Unit) {
    SignUpScreenContent(
        state = viewModel.state,
        events = SinginEvents(
            onEmailChange = viewModel::onEmailChange,
            onPasswordChange = viewModel::onPasswordChange,
            onUserChange = viewModel::onUserChange,
            onDateChange = viewModel::onDateChange,
            onLoginClick = backToLogin,
            onRegisterClick = viewModel::onRegisterClick
        )
    )
    if (viewModel.state.success){
        backToLogin(viewModel.state.email,viewModel.state.password)

    }
}

@Composable
private fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    events: SinginEvents,
    state: AccountRegisterState
) {
    Box(
        modifier = Modifier.fillMaxSize(),
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
                text = "Crear Cuenta",
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            // Campo de Email
            TextField(
                value = state.email,
                onValueChange = events.onEmailChange,
                label = { Text("Email") },
                isError = state.isEmailError,
                leadingIcon = { LeadingEmailIcon() },
                supportingText = {
                    Row {
                        Text(text = if (state.isEmailError) "Formato incorrecto" else "")
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = "${state.email.length}/30")
                    }
                }
            )
            // Campo de Usuario
            TextField(
                value = state.user,
                onValueChange = events.onUserChange,
                label = { Text("Usuario") },
                leadingIcon = { LeadingUserIcon() }
            )

            // Campo de Contraseña
            TextField(
                value = state.password,
                onValueChange = events.onPasswordChange,
                label = { Text("Contraseña") },
                leadingIcon = { LeadingPassIcon() },
                isError = state.isPasswordError,
                supportingText = {
                    if (state.isPasswordError) {
                        Text(text = "Formato incorrecto", color = Color.Red)
                    }
                }
            )
            // Campo de Fecha
            TextField(
                value = state.birthdate,
                onValueChange = events.onDateChange,
                label = { Text("Cumpleaños") },
                leadingIcon = { LeadingPassIcon() },
                isError = state.isDateError,
                supportingText = {
                    if (state.isDateError) {
                        Text(text = "Formato incorrecto", color = Color.Red)
                    }
                }
            )
            // Botón para Registrar
            Button(onClick = events.onRegisterClick) {
                Text("Registrarse")
            }

            // Enlace para volver al inicio de sesión
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Ya tienes una cuenta?")
                TextButton(onClick = { events.onLoginClick(state.email,state.password) }) {
                    Text("Acceder")
                }
            }
        }
    }
}

@Composable
fun LeadingUserIcon() {
    Icon(
        imageVector = Icons.Filled.Person,
        contentDescription = "User Icon",
        modifier = Modifier.size(24.dp),
        tint = MaterialTheme.colorScheme.primary
    )
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

