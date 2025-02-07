package com.example.loginlourdes.ui.theme.Login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginlourdes.Utils.validaEmail
import com.example.loginlourdes.domain.data.model.Account
import com.example.loginlourdes.domain.data.model.AccountException
import com.example.loginlourdes.domain.data.model.Session
import com.example.loginlourdes.domain.data.repository.AccountRepository
import com.example.loginlourdes.Base.ui.network.BaseResult
import com.example.loginlourdes.Base.ui.network.BaseResultList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "VIEWMODEL"
@HiltViewModel
class LoginViewModel @Inject constructor(
    val sesion:Session
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    /**
     * Al cambiar el email, valida que sea correcto.
     */

    suspend fun initfields(email: String,password: String){
        state = state.copy(email= email, password = password)
        sesion.saveUserSession(email,password,true)
    }

    fun onEmailChange(email: String) {
        // Reinicia errores previos
        state = state.copy(isEmailError = false, emailErrorFormat = "")

        // Ignorar espacios en blanco
        if (email.contains(' ')) return

        // Validar formato del email
        if (!validaEmail(email)) {
            state = state.copy(
                isEmailError = true,
                emailErrorFormat = "Email incorrecto",
                email = email
            )
            return
        }

        state = state.copy(email = email)
    }

    /**
     * Al cambiar la contraseña, reinicia errores previos.
     */
    fun onPasswordChange(password: String) {
        // Reinicia errores previos
        state = state.copy(isPasswordError = false, passwordErrorFormat = "")

        // Ignorar espacios en blanco
        if (password.contains(' ')) return

        // Validar longitud o reglas de la contraseña (opcional)
        if (password.length < 6) {
            state = state.copy(
                isPasswordError = true,
                passwordErrorFormat = "La contraseña debe tener al menos 6 caracteres",
                password = password
            )
            return
        }

        state = state.copy(password = password)
    }

    /**
     * Iniciar sesión.
     */
    fun onLoginClick() {
        if (state.isEmailError || state.isPasswordError || state.email.isEmpty() || state.password.isEmpty()) {
            state = state.copy(
                success = false,
                emailErrorFormat = "Por favor, revisa los campos"
            )
            return
        }

        state = state.copy(isLoading = true)

        viewModelScope.launch {
            val isValid = AccountRepository.validate(state.email, state.password)

            state = if (isValid) {
                state.copy(success = true, isLoading = false)
            } else {
                state.copy(
                    success = false,
                    isLoading = false,
                    emailErrorFormat = AccountException.NoExistAccount.message.toString()
                )
            }
        }
    }
}
