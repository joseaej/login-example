package com.example.loginlourdes.ui.accounts

import com.example.loginlourdes.domain.data.model.Account

sealed class AccountListState {
    data object NoData:AccountListState()
    data object Loading:AccountListState()
    //data object Success:AccountListState()
    data class Success(var datalist:List<Account>):AccountListState()
}
