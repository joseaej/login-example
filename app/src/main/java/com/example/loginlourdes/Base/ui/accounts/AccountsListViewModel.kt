package com.example.loginlourdes.Base.ui.accounts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginlourdes.Base.icon.SortAZ
import com.example.loginlourdes.Base.icon.SortZA
import com.example.loginlourdes.domain.data.model.account.Account
import com.example.loginlourdes.domain.data.repository.AccountRepositoryBD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountsListViewModel @Inject constructor(
    private val accountRepository: AccountRepositoryBD
) : ViewModel() {

    var state by mutableStateOf<AccountListState>(AccountListState.Loading)
        private set
    var isAZSort by mutableStateOf(true)
        private set

    var dataset by mutableStateOf(emptyList<Account>())
    init {
        getList()
    }

    fun getList() {
        viewModelScope.launch {
            state = AccountListState.Loading
            accountRepository.getData().collect { accounts ->
                state = if (accounts.isNotEmpty()) {
                    AccountListState.Success(accounts)
                } else {
                    AccountListState.NoData
                }
            }
        }
    }
    fun delete(account: Account) {
        viewModelScope.launch {
            accountRepository.delete(account)
            val updatedList = (state as? AccountListState.Success)?.datalist?.toMutableList()
            updatedList?.remove(account)

            state = if (updatedList.isNullOrEmpty()) {
                AccountListState.NoData
            } else {
                AccountListState.Success(updatedList)
            }
        }
    }
    fun shortAccountList(){
        if (isAZSort){
            viewModelScope.launch {
                val updatedList = (state as? AccountListState.Success)?.datalist?.sortedBy { it.email.value }
                state = if (updatedList.isNullOrEmpty()) {
                    AccountListState.NoData
                } else {
                    AccountListState.Success(updatedList)
                }
            }
            isAZSort = false
        }else{
            viewModelScope.launch {
                (state as? AccountListState.Success)?.let { successState ->
                    val reversedList = successState.datalist.sortedByDescending { it.email.value }
                    state = if (reversedList.isEmpty()) {
                        AccountListState.NoData
                    } else {
                        AccountListState.Success(reversedList)
                    }
                }
            }
            isAZSort = true
        }
    }
    @Composable
    fun onChangeIcon(): ImageVector {
        return if (isAZSort) {
            SortZA()
        } else {
            SortAZ()
        }
    }
}
