package com.example.loginlourdes.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.loginlourdes.Base.ui.accounts.AccountScreen
import com.example.loginlourdes.Base.ui.accounts.AccountsListViewModel

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
            goToDetails = {
            },
            goToCreation = {navController.navigate(LoginGraph.signUp())},
            openDrawer = {}
        )
    }
}
