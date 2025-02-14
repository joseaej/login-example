package com.example.loginlourdes.di

import android.content.Context
import android.content.res.Resources
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import com.example.loginlourdes.domain.data.LoginDatabase
import com.example.loginlourdes.domain.data.dao.AccountDao
import com.example.loginlourdes.domain.data.dao.BusinessDao
import com.example.loginlourdes.domain.data.dao.PersonalDao
import com.example.loginlourdes.domain.data.model.Session
import com.example.loginlourdes.domain.data.repository.AccountRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton  // <-- Corrección aquí

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAccountRepository(): AccountRepository {
        return AccountRepository
    }
    // Implementar para internacionalizar strings
    @Provides
    @Singleton
    fun provideStringResources(@ApplicationContext context: Context): Resources {
        return context.resources
    }

    /**
     * Método que provee el DataStore (api-valor) de la sesión
     */
    @Singleton
    @Provides
    fun provideSessionDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = { emptyPreferences() }),
            produceFile = { context.preferencesDataStoreFile(Session.DATA) }
        )
    }

    @Singleton
    @Provides
    fun providesLoginDatabase(@ApplicationContext context: Context):LoginDatabase{
        //Se crea la base de datos
        return LoginDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideAccountDao(loginDatabase: LoginDatabase):AccountDao{
        //Se crea la base de datos
        return loginDatabase.getAccountDao()
    }
    @Singleton
    @Provides
    fun provideBusinessDao(loginDatabase: LoginDatabase):BusinessDao{
        //Se crea la base de datos
        return loginDatabase.getBusinessDao()
    }
    @Singleton
    @Provides
    fun providePersonalDao(loginDatabase: LoginDatabase):PersonalDao{
        //Se crea la base de datos
        return loginDatabase.getPersonalDao()
    }
}
