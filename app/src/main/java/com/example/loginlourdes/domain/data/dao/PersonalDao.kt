package com.example.loginlourdes.domain.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.loginlourdes.domain.data.model.account.Business
import com.example.loginlourdes.domain.data.model.account.Personal
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonalDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(personal: Personal)
    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(personal: Personal)
    @Delete
    suspend fun delete(personal: Personal)

    @Query("SELECT * FROM Personal WHERE id=:personalID")
    suspend fun getPersonalById(personalID:Int): Personal?
    @Query("SELECT * FROM Personal ORDER BY nif ASC")
    fun getAllBusiness(): Flow<List<Personal>>
}