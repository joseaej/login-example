package com.example.loginlourdes.domain.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.loginlourdes.domain.data.model.account.Account
import com.example.loginlourdes.domain.data.model.account.Business
import kotlinx.coroutines.flow.Flow

@Dao
interface BusinessDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(business: Business)
    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(business: Business)
    @Delete
    suspend fun delete(business: Business)

    @Query("SELECT * FROM Business WHERE id=:businessID")
    suspend fun getBusinessById(businessID:Int): Business?
    @Query("SELECT * FROM Business WHERE company=:company")
    suspend fun getBusinessByCompany(company:String): Business?
    @Query("SELECT * FROM Business ORDER BY company ASC")
    fun getAllBusiness(): Flow<List<Business>>
}