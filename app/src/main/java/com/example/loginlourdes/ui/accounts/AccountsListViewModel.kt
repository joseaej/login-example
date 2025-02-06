package com.example.loginlourdes.ui.accounts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginlourdes.domain.data.model.Account
import com.example.loginlourdes.domain.data.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountsListViewModel @Inject constructor():ViewModel() {
    var state by mutableStateOf<AccountListState>(AccountListState.Loading)
     private set
    init {
        getList()
    }

    fun getList(){
        // se inicia la corrutina
        viewModelScope.launch {
            state = AccountListState.Loading
            AccountRepository.getData().collect(){ accounts ->
                if(accounts.isNotEmpty()) {
                    //1. Si hay datos
                    state = AccountListState.Success(accounts)
                    //list = accounts.toList()
                }
                else{
                //2. Si no hay datos
                state=AccountListState.NoData
                }
            }
        }
    }
}