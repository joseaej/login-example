package com.example.loginlourdes.domain.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.loginlourdes.domain.data.model.account.Account
import kotlinx.coroutines.flow.Flow

/**
* Account dao
* Contiene metodos para crear,seleccionar,actualizar,borrar,etc(CRUD)
* objetos de Account en la base de datos Sqlite
*
* @constructor Create empety Account dao
* **/

@Dao
interface  AccountDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(account: Account)
    @Delete
    suspend fun delete(account: Account)
    @Update
    suspend fun update(account: Account)
    @Query("SELECT * FROM Account WHERE id=:accountID")
    suspend fun getAccountByID(accountID:Int): Account?
    @Query("SELECT * FROM Account WHERE email=:email")
    suspend fun getAccountByEmail(email:String): Account?
    @Query("SELECT * FROM Account ORDER BY name ASC")
    fun getAllAccounts(): Flow<List<Account>>

    @Query("Select * from Account where email=:email AND password=:password")
    fun validate(email: String, password: String): Account?
}