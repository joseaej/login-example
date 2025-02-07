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

object AccountGraph {
    const val ROUTE = "account_graph"

    fun accountList() = "$ROUTE/accountList"
}

fun NavGraphBuilder.accountGraph(
    navController: NavController,
) {
    navigation(startDestination = AccountGraph.accountList(), route = AccountGraph.ROUTE) {
        accountList(navController)
    }
}
private fun NavGraphBuilder.accountList(navController: NavController) {
    composable(route = AccountGraph.accountList()) {
        val accountsListViewModel: AccountsListViewModel = hiltViewModel()
        AccountScreen(accountsListViewModel,
            goToDetails = {},
            goToCreation = {},
            openDrawer = {}
        )
    }
}
