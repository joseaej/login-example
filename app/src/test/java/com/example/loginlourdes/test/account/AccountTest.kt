package com.example.loginlourdes.test.account

import com.example.loginlourdes.domain.data.model.account.Account
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.assertThrows
import java.util.Date

class AccountTest{
    data class fakeAccount(
        val id:Int,
        val email:String,
        val password:String,
        val name:String
    )
    @Test
    fun `cuenta valida es true` (){
        val account = Account.create(
            id = 1,
            email = "jose@gmail.com",
            name = "jose",
            password = "1234567",
            surname = "joseplay",
            birthdate = Date(),
            displayName = "dasda",
            firebaseUID = "firebase1234"
        )
        assertNotNull(account) //la cuenta no puede ser nula
        assertEquals("jose@gmail.com",account.email.value)
        assertEquals(1,account.id)
        assertEquals("jose",account.name)
        assertEquals("1234567",account.password)
        assertEquals("joseplay",account.surname)
        assertEquals("firebase1234",account.firebaseUID)
        assertEquals("dasda",account.displayName)
        assertEquals(account.birthdate,account.birthdate)
        assertNotNull(account.createdAt)
        assertNotNull(account.updatedAt)
    }
    @Test
    fun `contraseña es corta true` (){
        val exception = assertThrows<IllegalArgumentException>{
            val account = Account.create(
                id = 1,
                email = "jose@gmail.com",
                name = "jose",
                password = "12",
                surname = "joseplay",
                birthdate = Date(),
                displayName = "dasda",
                firebaseUID = "firebase1234"
            )
        }
        assertEquals("La contraseña debe tener al menos 6 caracteres.",exception.message)
    }
    @Test
    fun `nombre vacio true` (){
        val exception = assertThrows<IllegalArgumentException>{
            val account = Account.create(
                id = 1,
                email = "jose@gmail.com",
                name = "",
                password = "1234567",
                surname = "joseplay",
                birthdate = Date(),
                displayName = "dasda",
                firebaseUID = "firebase1234"
            )
        }
        assertEquals("El nombre no puede estar vacío.",exception.message)

    }
    @Test
    fun `apellido esta vacio true` (){
        val exception = assertThrows<IllegalArgumentException>{
            val account = Account.create(
                id = 1,
                email = "jose@gmail.com",
                name = "dasdasdasdas",
                password = "1234567",
                surname = "",
                birthdate = Date(),
                displayName = "",
                firebaseUID = "firebase1234"
            )
        }
        assertEquals("El apellido no puede estar vacío.",exception.message)
    }
    @Test
    fun `class de cuentas diferentes  true` (){
        val account = Account.create(
            id = 1,
            email = "jose@gmail.com",
            name = "jose",
            password = "1234567",
            surname = "joseplay",
            birthdate = Date(),
            displayName = "dasda",
            firebaseUID = "firebase1234"
        )
       val fakeAccount = fakeAccount(id = 1,
           email = "jose@gmail.com",
           name = "jose",
           password = "1234567",
       )
        assertNotEquals(account.javaClass,fakeAccount.javaClass)
    }
}