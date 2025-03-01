package com.example.loginlourdes.ui.theme.Login

import android.accounts.Account
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginlourdes.Base.ui.network.BaseResult
import com.example.loginlourdes.utils.validaEmail
import com.example.loginlourdes.domain.data.model.AccountException
import com.example.loginlourdes.domain.data.model.Session
import com.example.loginlourdes.domain.data.repository.AccountRepository
import com.example.loginlourdes.domain.data.repository.AccountRepositoryBD
import com.example.loginlourdes.utils.validarPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "VIEWMODEL"

@HiltViewModel
class LoginViewModel @Inject constructor(
    val sesion: Session,
    val accountRepository: AccountRepositoryBD
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    /**
     * Al cambiar el email, valida que sea correcto.
     */
    suspend fun initfields(email: String, password: String) {
        if (state.email.isEmpty() || state.password.isEmpty()) {
            state = state.copy(email = email, password = password)
            sesion.saveUserSession(email, password, true)
        }
    }

    fun onEmailChange(email: String){
        state.passwordErrorFormat.let {
            state = state.copy(isEmailError = false, emailErrorFormat = "")
        }

        //1. Si el usuario pulsa caracter en blanco , ni se tiene en cuenta
        if (email.contains(' '))return
        //2. Si no es válido se modifica los valores del state
        if (!validaEmail(email)){
            state = state.copy(isEmailError = true, emailErrorFormat = "Email incorrecto", email = email)
        }
        state = state.copy(email = email)
    }
    fun onPasswordChange(password: String){
        state.passwordErrorFormat.let {
            state = state.copy(isPasswordError = false, passwordErrorFormat = "")
        }

        //1. Si el usuario pulsa caracter en blanco , ni se tiene en cuenta
        if (password.contains(' '))return
        //2. Si no es válido se modifica los valores del state
        if (!validarPassword(password)){
            state = state.copy(isPasswordError = true, passwordErrorFormat = "Contraseña incorrecto", password = password)
        }
        state = state.copy(password = password)
    }

    /**
     * Iniciar sesión.
     */
    fun onLoginClick() {
        // Verificar si hay errores de validación
        if (state.isEmailError || state.isPasswordError || state.email.isEmpty() || state.password.isEmpty()) {
            state = state.copy(
                success = false,
                emailErrorFormat = "Por favor, revisa los campos"
            )
            return
        }

        state = state.copy(isLoading = true)

        viewModelScope.launch {
            /*
            when (val result = accountRepositoryBD.validate(state.email, state.password)) {
                is BaseResult.Success -> {
                    state = state.copy(success = true)
                }
                is BaseResult.Error -> {
                    when (result.exception) {
                        is AccountException.NoExistAccount -> {
                            state = state.copy(emailErrorFormat = "La cuenta no existe", isEmailError = true)
                        }
                        else -> {
                            state = state.copy(emailErrorFormat = "Error desconocido", isEmailError = true)
                        }
                    }
                }
            }*/
            viewModelScope.launch {
                val isValid = accountRepository.validate(state.email, state.password)

                state= when(isValid){
                    is BaseResult.Success ->{
                        state.copy(success = true, isLoading = false)
                    }
                    else->{
                        state.copy(
                            success = false,
                            isLoading = false,
                            emailErrorFormat = AccountException.NoExistAccount.message.toString()
                        )
                    }
                }
            }
        }

    }
}
