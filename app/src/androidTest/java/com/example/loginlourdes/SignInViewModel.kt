package com.example.loginlourdes

import com.example.loginlourdes.ui.theme.Login.LoginViewModel
import com.example.loginlourdes.domain.data.repository.AccountRepositoryBD
import com.example.loginlourdes.Base.ui.network.BaseResult
import com.example.loginlourdes.domain.data.model.account.Account
import com.example.loginlourdes.domain.data.model.AccountException
import com.example.loginlourdes.domain.data.model.Email
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.util.Date

class LoginViewModelTest {

    private val mockRepository: AccountRepositoryBD = mockk(relaxed = true)
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        viewModel = LoginViewModel(mockk(), mockRepository)
    }

    @Test
    fun `email validation should show error when email is invalid`() {
        viewModel.onEmailChange("invalid-email")
        assertTrue("Debe marcar error en el email", viewModel.state.isEmailError)
    }

    @Test
    fun `password validation should show error when password is invalid`() {
        viewModel.onPasswordChange("123")
        assertTrue("Debe marcar error en la contraseña", viewModel.state.isPasswordError)
    }

    @Test
    fun `login should fail with incorrect credentials`() = runBlocking {
        coEvery { mockRepository.validate(any(), any()) } returns BaseResult.Error(AccountException.NoExistAccount)

        viewModel.onLoginClick()

        assertTrue("Debe fallar el login", !viewModel.state.success)
    }
    @Test
    fun `login should fail with correct credentials`() = runBlocking {
        coEvery { mockRepository.validate(any(), any()) } returns BaseResult.Success(data = Account.create(
            id = 1,
            email = "jose@gmail.com",
            name = "jose",
            password = "1234567",
            surname = "joseplay",
            birthdate = Date(),
            displayName = "dasda",
            firebaseUID = "firebase1234"
        ),)

        viewModel.onLoginClick()

        assertTrue("Debe fallar el login", !viewModel.state.success)
    }
}