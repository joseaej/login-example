package com.example.loginlourdes.Base.ui.Signin

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginlourdes.Login.AccountRegisterState
import com.example.loginlourdes.utils.validaEmail
import com.example.loginlourdes.utils.validarDate
import com.example.loginlourdes.utils.validarPassword
import com.example.loginlourdes.domain.data.model.account.Account
import com.example.loginlourdes.domain.data.repository.AccountRepository
import com.example.loginlourdes.domain.data.repository.AccountRepositoryBD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

const val TAG = "VIEWMODEL"

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountRepository: AccountRepositoryBD
):ViewModel() {
    //Opcion Google
    private val _stateGoogle = mutableStateOf(AccountRegisterState())    //si mutable
    val stateGoogle: State<AccountRegisterState> = _stateGoogle    //no mutable
    //Opcion Developers
    var state by mutableStateOf(AccountRegisterState())
        private set
    var account: Account = Account.empty()
    /**
     * On email change, comprueba que el email sea correcto
     *
     * @param email
     */
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
    fun onUserChange(user: String){
        state.userErrorFormat.let {
            state = state.copy(isUserError = false,  userErrorFormat = "")
        }

        //1. Si el usuario pulsa caracter en blanco , ni se tiene en cuenta
        if (user.contains(' '))return
        //2. Si no es válido se modifica los valores del state
        if (!validarPassword(user)){
            state = state.copy(isUserError = true, userErrorFormat = "Contraseña incorrecto", user = user)
        }
        state = state.copy(user = user)
    }
    fun onDateChange(birthdate:String){
        state.dateErrorFormat.let {
            state = state.copy(isDateError = false, dateErrorFormat = "")
        }

        //1. Si el usuario pulsa caracter en blanco , ni se tiene en cuenta
        if (birthdate.contains(' '))return
        //2. Si no es válido se modifica los valores del state
        if (!validarDate(birthdate)){
            state = state.copy(isDateError = true, dateErrorFormat = "Fecha incorrecto", birthdate = birthdate)
        }
        state = state.copy(birthdate = birthdate)
    }
    fun onRegisterClick() {
        if (state.isEmailError || state.isPasswordError || state.isUserError || state.isDateError) {
            state = state.copy(success = false)
            return
        }
        state = state.copy(success = true)
        updateAccount()
        viewModelScope.launch {
            accountRepository.insert(account)
        }
    }
    private fun updateAccount(){
        account.email.value = state.email
        account.name = state.user
        account.password = state.password
        account.birthdate = Date(state.birthdate)
    }

    fun reset(){
        state = state.copy(success = false, user = "", email = "", password = "", birthdate = "")
        account = Account.empty()
    }
}