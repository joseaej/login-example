package com.example.loginlourdes.domain.data.model.account
import android.provider.ContactsContract
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.loginlourdes.domain.data.model.Email
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import kotlin.math.absoluteValue

@Entity
data class Account constructor(
    @PrimaryKey
    var id: Int,
    val email: Email,
    var password: String,
    var name: String,
    val surname: String,
    @ColumnInfo (name="display_name")
    val displayName: String?,
    var birthdate: Date?,
    @ColumnInfo(name="firebase_uid")
    val firebaseUID: String? = null,
    @ColumnInfo(name="photo_url")
    val photoURL: String? = null,
    @ColumnInfo(name="created_at")
    val createdAt: String = getCurrentTimestamp(),
    @ColumnInfo(name="update_at")// Se asigna el instante actual
    val updatedAt: String = getCurrentTimestamp()

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Account

        return email == other.email
    }

    override fun hashCode(): Int {
        return email.hashCode()
    }
    companion object {

        private fun getCurrentTimestamp(): String {
            val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
            return sdf.format(Date())  // Convierte la fecha actual en String
        }

        // Método de fábrica: puede servir para añadir validaciones u otra lógica

        fun create(
            id: Int,
            email: String,
            password: String,
            name: String,
            surname: String,
            displayName: String,
            birthdate: Date?,
            firebaseUID: String?,

            ): Account {
            // Aquí se podrían agregar validaciones, transformación de datos, etc.
            // Validaciones básicas
            require(password.length >= 6) { "La contraseña debe tener al menos 6 caracteres." }
            require(name.isNotBlank()) { "El nombre no puede estar vacío." }
            require(surname.isNotBlank()) { "El apellido no puede estar vacío." }

            return Account( id, Email(email), password, name, surname, displayName, birthdate, firebaseUID)
        }
        fun empty(): Account {
            return Account(
                id = 0,
                email = Email(""),
                password = "",
                name = "",
                surname = "",
                displayName = null,
                birthdate = null,
                firebaseUID = null,
                photoURL = null,
                createdAt = getCurrentTimestamp(),
                updatedAt = getCurrentTimestamp()
            )
        }
        fun generarCuentaId(): Int {
            return UUID.randomUUID().hashCode().absoluteValue % 900000 + 100000
        }

    }

}