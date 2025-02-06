package com.example.loginlourdes.domain.data.model


import android.content.Context
import android.content.res.Resources
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

class Session @Inject constructor(private val dataStore: DataStore<Preferences>) {
    companion object {
        const val DATA = "Data"
        private const val IS_LOGIN = "IsLogin"
        private const val EMAIL = "Email"
        private const val PASSWORD = "Password"
        val email = stringPreferencesKey(EMAIL)
        val isLogin = booleanPreferencesKey(IS_LOGIN)
        val password = stringPreferencesKey(PASSWORD)
    }

    fun isUserLoggedIn(): Flow<Boolean> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preference ->
            preference[isLogin] ?: false
        }
    }

    suspend fun saveUserSession(userEmail: String, userPassword: String, isUserLoggedIn: Boolean) {
        dataStore.edit { preference ->
            preference[email] = userEmail
            preference[password] = userPassword
            preference[isLogin] = isUserLoggedIn
        }
    }
    suspend fun setUserLoggedIn(isUserLoggedIn: Boolean) {
        dataStore.edit { preference ->
            preference[isLogin] = isUserLoggedIn
        }
    }

    fun getEmail(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[email] ?: ""
        }
    }

    suspend fun setEmail(userEmail: String) {
        dataStore.edit { preference ->
            preference[email] = userEmail
        }
    }

    fun getPassword(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { value: Preferences ->
            value[password] ?: ""
        }
    }

    suspend fun setPassword(userPassWord: String) {
        dataStore.edit { preference ->
            preference[password] = userPassWord
        }
    }
}