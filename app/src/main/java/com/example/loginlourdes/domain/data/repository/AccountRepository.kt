package com.example.loginlourdes.domain.data.repository

import com.example.loginlourdes.domain.data.model.Email
import com.example.loginlourdes.domain.data.model.account.Account
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Locale


object AccountRepository {

    //region Simulacion de datos
    private var dataSet:MutableList<Account> = mutableListOf()
    init {
        initialice()
    }
    private fun initialice(){
        dataSet.apply {
            add(Account(
                id = 2,
                email = Email("usuario2@example.com"),
                password = "pass456",
                name = "María",
                birthDate = SimpleDateFormat("dd/mm/yyyy", Locale.getDefault()).parse("02/22/2004"),
                lastName = "López",
                displayName = "MariaL",
            ),)
            add(Account(
                id = 1,
                email = Email("usuario1@example.com"),
                password = "password123",
                name = "José",
                birthDate = SimpleDateFormat("dd/mm/yyyy", Locale.getDefault()).parse("02/22/2004"),
                lastName = "García",
                displayName = "PepeG",
            ))
        }

    }
    //endregion

    fun validate(email: String, password: String, date: String = ""): Boolean {

        return dataSet.any {
            it.email.value == email && it.password == password
        }
    }

    fun deleteAccount(account: Account):Boolean{
        if (dataSet.contains(account)){
            return false
        }else{
            account.id = dataSet.size+1
            dataSet.remove(account)
            return true
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