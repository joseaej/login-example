package com.example.loginlourdes.domain.data.repository

import android.util.Log
import com.example.loginlourdes.domain.data.model.Account
import com.example.loginlourdes.domain.data.model.AccountException
import com.example.loginlourdes.Base.ui.network.BaseResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


object AccountRepository {

    //region Simulacion de datos
    private var dataSet:MutableList<Account> = mutableListOf()
    init {
        initialice()
    }
    private fun initialice(){
        dataSet.add(
            Account(email = "jose@gmail.com",
                password = "12312312dasa321",
            )
        )
    }
    //endregion

    fun validate(email: String, password: String, date: String = ""): Boolean {

        return dataSet.any {
            it.email == email && it.password == password
        }
    }

    fun getData(): Flow<List<Account>> {
        // una consulta a la abase de datos ,cuyo DAO nos daria un flow.
        return flow { emit(dataSet) }
    }
    fun addAccount(account: Account):Boolean{
        if (dataSet.contains(account)){
            return false
        }else{
            account.id = dataSet.size+1
            dataSet.add(account)
            return true
        }
    }
}