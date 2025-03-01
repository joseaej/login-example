package com.example.loginlourdes

import com.example.loginlourdes.Base.ui.accounts.AccountListState
import com.example.loginlourdes.Base.ui.accounts.AccountsListViewModel
import com.example.loginlourdes.domain.data.dao.AccountDao
import com.example.loginlourdes.domain.data.model.account.Account
import com.example.loginlourdes.domain.data.repository.AccountRepositoryBD
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.util.Date

class AccountListViewModeTest {
    //se crea un mockito del respoitorio y por dependencia del dao
    private val mockDao:AccountDao = mockk(relaxed = true)
    private val repository:AccountRepositoryBD=AccountRepositoryBD(mockDao)
    private lateinit var viewModel:AccountsListViewModel
    @Before
    fun setup(){
        every { repository.getData() } returns flowOf(
            listOf(
                Account.create(
                    id = 1,
                    email = "jose@gmail.com",
                    name = "jose",
                    password = "1234567",
                    surname = "joseplay",
                    birthdate = Date(),
                    displayName = "dasda",
                    firebaseUID = "firebase1234"
                ),
                Account.create(
                    id = 2,
                    email = "jose2@gmail.com",
                    name = "jose2",
                    password = "12345672",
                    surname = "joseplay2",
                    birthdate = Date(),
                    displayName = "dasd2a",
                    firebaseUID = "firebase1234"
                )
            )
        )
        viewModel = AccountsListViewModel(repository) // Inicialización del ViewModel

    }
    @Test
    fun getList_whewnDataIsAvalible(){
        runBlocking {
            viewModel.getList()
            delay(2000)
            assertTrue("el estado debe ser Sucess",viewModel.state is AccountListState.Success)

            val dataSuccess = viewModel.state is AccountListState.Success;
            assertTrue("La lista de cuenta no pued eser vacia",viewModel.dataset.isEmpty())
        }
    }
    @Test
    fun loading(){
        runBlocking {
            viewModel.state
            delay(5000)
            assertTrue("el estado debe ser loading",viewModel.state is AccountListState.Loading)
        }
    }
}