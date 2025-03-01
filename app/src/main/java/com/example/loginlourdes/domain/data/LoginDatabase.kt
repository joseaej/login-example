package com.example.loginlourdes.domain.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.loginlourdes.domain.data.converter.Converters
import com.example.loginlourdes.domain.data.dao.AccountDao
import com.example.loginlourdes.domain.data.dao.BusinessDao
import com.example.loginlourdes.domain.data.dao.PersonalDao
import com.example.loginlourdes.domain.data.model.Email
import com.example.loginlourdes.domain.data.model.account.Account
import com.example.loginlourdes.domain.data.model.account.Addres
import com.example.loginlourdes.domain.data.model.account.Business
import com.example.loginlourdes.domain.data.model.account.Personal
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executors


//En las anotaciones en Kotlin los arrays es con corchetes
@Database(
    version = 3,
    entities = [Account::class,Personal::class,Business::class],
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class LoginDatabase : RoomDatabase() {
    abstract fun getAccountDao(): AccountDao
    abstract fun getBusinessDao(): BusinessDao
    abstract fun getPersonalDao(): PersonalDao
    companion object {
        /**
         * La variable se guarda en memoria. Cualquier cambio realizado en la variable por un hilo
         * se refleja de inmediado y es visible al resto de hilos. No hay copias antiguas o nulas.
         */
        @Volatile
        private var INSTANCE: LoginDatabase? = null

        fun getDatabase(context: Context): LoginDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LoginDatabase::class.java,
                    "login_database.db"
                )
                    // Callback para pre-poblar la base de datos
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Se utiliza un executor para realizar la inserción en un hilo de fondo
                            //Las tareas se ejecutan de forma secuencial en un hilo/s
                            Executors.newSingleThreadExecutor().execute {
                                INSTANCE?.let { database ->
                                    prepopulateDatabase(database)
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
        private fun prepopulateDatabase(database:LoginDatabase){
            val accountDao = database.getAccountDao()
            val personalDao = database.getPersonalDao()
            val businessDao = database.getBusinessDao()
            //bloque de ejecución secuencial

            runBlocking {
                /*
                accountDao.insert(
                    Account(
                        id = 1,
                        email = Email("usuario1@example.com"),
                        password = "password123",
                        name = "José",
                        birthdate = SimpleDateFormat("dd/mm/yyyy", Locale.getDefault()).parse("02/22/2004"),
                        displayName = "García",
                        firebaseUID = "firebase1",
                        surname = "jose"
                    )
                )
                accountDao.insert(
                    Account(
                        id = 2,
                        email = Email("usuario2@example.com"),
                        password = "pass456",
                        name = "María",
                        birthdate = SimpleDateFormat("dd/mm/yyyy", Locale.getDefault()).parse("02/22/2004"),
                        displayName = "García",
                        firebaseUID = "firebase2",
                        surname = "maria"
                    ),
                )
                accountDao.insert(
                    Account(
                        id = 3,
                        email = Email("usuario3@example.com"),
                        password = "clave789",
                        name = "Carlos",
                        birthdate = SimpleDateFormat("dd/mm/yyyy", Locale.getDefault()).parse("02/22/2004"),
                        displayName = "García",
                        firebaseUID = "firebase3",
                        surname = "carlitos"
                    )
                )
                personalDao.insert(
                    Personal(
                        idAccount = 3,
                        nif = "32321231J",
                        addres = Addres(
                            id = 1,
                            street = "C/Competa,31",
                            city = "Malaga",
                            country = "España",
                            postalCode = 29003,
                        )
                    )
                )
                businessDao.insert(
                    Business(
                        idAccount = 2,
                        cif = "32321231J",
                        company = "Google",
                        addres = Addres(
                            id = 1,
                            street = "C/Competa,31",
                            city = "Malaga",
                            country = "España",
                            postalCode = 29003,
                        )
                    )
                )*/
            }
        }
    }
}