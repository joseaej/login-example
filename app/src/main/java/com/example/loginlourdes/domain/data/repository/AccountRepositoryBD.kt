package com.example.loginlourdes.domain.data.repository

import androidx.compose.runtime.toMutableStateList
import com.example.loginlourdes.domain.data.model.account.Account
import com.example.loginlourdes.domain.data.model.AccountException
import com.example.loginlourdes.Base.ui.network.BaseResult
import com.example.loginlourdes.domain.data.dao.AccountDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import javax.inject.Inject


class AccountRepositoryBD @Inject constructor(private val accountDao: AccountDao) {


    suspend fun validate(email: String, password: String, date: String = ""): BaseResult<Account> {
        val account = accountDao.validate(email,password)
        val result:BaseResult<Account> = account?.let{
            BaseResult.Success(account)
        }?:BaseResult.Error(AccountException.NoExistAccount)
        return result
    }

    fun getData(): Flow<List<Account>> {
        // una consulta a la abase de datos ,cuyo DAO nos daria un flow.
        return accountDao.getAllAccounts()
    }

    suspend fun getDataList(): MutableList<List<Account>> {
        // una consulta a la abase de datos ,cuyo DAO nos daria un flow.
        return (accountDao.getAllAccounts().toList().toMutableStateList())
    }
    suspend fun insert(account: Account): BaseResult<Account> =
        if (accountDao.getAccountByEmail(account.email.toString())!=null) {
            accountDao.insert(account)
            BaseResult.Success(account)
        } else{
            BaseResult.Error(AccountException.TakenEmail(account.email.toString()))
        }

    suspend fun delete(account: Account): BaseResult<Account> =
        if (accountDao.getAccountByEmail(account.email.toString())!=null) {
            accountDao.delete(account)
            BaseResult.Success(account)
        } else{
            BaseResult.Error(AccountException.TakenEmail(account.email.toString()))
        }
}