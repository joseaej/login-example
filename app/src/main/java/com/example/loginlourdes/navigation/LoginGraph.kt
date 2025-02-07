package com.example.loginlourdes.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.loginlourdes.SignUpScreen
import com.example.loginlourdes.Base.ui.Signin.SignInViewModel
import com.example.loginlourdes.Base.ui.accounts.AccountScreen
import com.example.loginlourdes.Base.ui.accounts.AccountsListViewModel
import com.example.loginlourdes.ui.theme.Login.LoginScreen
import com.example.loginlourdes.ui.theme.Login.LoginViewModel

object LoginGraph {
    const val ROUTE = "login_graph"

    fun login() = "$ROUTE/login/{email}/{password}"
    fun signUp() = "$ROUTE/signup"
}

fun NavGraphBuilder.loginGraph(
    navController: NavController,
) {
    navigation(startDestination = LoginGraph.signUp(), route = LoginGraph.ROUTE) {
        login(navController)
        signUp(navController)
    }
}

private fun NavGraphBuilder.login(navController: NavController) {
    composable(
        route = LoginGraph.login(),
        arguments = listOf(
            navArgument("email") { type = NavType.StringType },
            navArgument("password") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val email = backStackEntry.arguments?.getString("email") ?: ""
        val password = backStackEntry.arguments?.getString("password") ?: ""
        val loginViewModel: LoginViewModel = hiltViewModel()
        LoginScreen(
            loginViewModel = loginViewModel,
            email = email,
            password = password,
            goToRegister = {
                navController.navigate(LoginGraph.signUp())
            },
            goToAccounts = {
                navController.navigate(AccountGraph.ROUTE)
            }
        )
    }
}

private fun NavGraphBuilder.signUp(navController: NavController) {
    composable(route = LoginGraph.signUp()) {
        val signInViewModel: SignInViewModel = hiltViewModel()
        SignUpScreen(
            backToLogin = { email, password ->
                navController.navigate(LoginGraph.login().replace("{email}", email).replace("{password}", password))
            },
            viewModel = signInViewModel,
        )
    }
}