package com.example.loginlourdes.di

import android.content.Context
import android.content.res.Resources
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import com.example.loginlourdes.domain.data.model.Session
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton  // <-- Corrección aquí

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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
}
