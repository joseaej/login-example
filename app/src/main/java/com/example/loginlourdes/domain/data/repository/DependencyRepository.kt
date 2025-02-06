package com.example.loginlourdes.domain.data.repository

import com.example.loginlourdes.domain.data.model.Account
import com.example.loginlourdes.domain.data.model.Dependency
import com.example.loginlourdes.ui.network.BaseResult

object DependencyRepository {
    //region Simulacion de datos
    private var dataSet:MutableList<Dependency> = mutableListOf()
    init {
        initialice()
    }
    private fun initialice(){
        dataSet.add(
            Dependency("Dam","3390"

            )
        )
    }
    //endregion

    suspend fun validate(email: String, password: String):BaseResult<Dependency> {
        return BaseResult.Success(Dependency(nombre = email, codigo = password))
    }
}