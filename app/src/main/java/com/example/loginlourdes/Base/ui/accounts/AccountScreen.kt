package com.example.loginlourdes.Base.ui.accounts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.loginlourdes.Base.composable.Actions
import com.example.loginlourdes.Base.composable.BaseTopAppBar
import com.example.loginlourdes.Base.composable.BaseTopAppBarState
import com.example.loginlourdes.Base.icon.Filter
import com.example.loginlourdes.domain.data.model.Account
import com.example.loginlourdes.Base.ui.NoDataScreen
import com.example.loginlourdes.Base.ui.loading.LoadingScreen
import com.example.loginlourdes.R

data class AccountListEvents(
    val openDrawer: () -> Unit,
    val goToCreation: () -> Unit,
    val goToDetails: (Int) -> Unit,
    val goToDelete:(Account) ->Unit,
)


@Composable
fun AccountScreen(viewModel: AccountsListViewModel, openDrawer:()->Unit,
                  goToCreation:()->Unit,
                  goToDetails:(Int)->Unit
      ) {
    //1. Control de la logica y el estado

    LaunchedEffect (Unit){
        viewModel.getList()
    }
    val events =  AccountListEvents(
        openDrawer = openDrawer,
        goToCreation = goToCreation,
        goToDetails = goToDetails,
        goToDelete = {account -> viewModel.delete(account)}
    )
    Scaffold (
        topBar = {BaseTopAppBar(appBarState(events))},
        floatingActionButton = {
            FloatingActionButton(onClick = events.goToCreation) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Icono ventana",

                )
            }
        },
    ){ innerPadding ->
        when (val state = viewModel.state) { // Guardamos el estado en una variable local
            AccountListState.NoData -> NoDataScreen()
            is AccountListState.Success -> AccountListContent(accounts = state.datalist, modifier = Modifier.padding(innerPadding))
            AccountListState.Loading -> LoadingScreen()
        }
    }

}

@Composable
fun AccountListContent(modifier: Modifier = Modifier, accounts: List<Account>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(accounts) { account ->
            AccountItem(text = account.email)
        }
    }
}

@Composable
fun AccountItem(modifier: Modifier = Modifier, text: String = "Account") {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 6.dp,
        modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Account Icon",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}



@Composable
fun appBarState(events: AccountListEvents): BaseTopAppBarState {
    return  BaseTopAppBarState(
        //tittle = stringResource(R.string.title_account_list_screen),
        tittle = "Titulo",
        upAction = {},
        icon = Icons.Filled.Menu,
        acctions = listOf(
            Actions(
                //nameTitle = stringResource(R.string.list_acction_filter),
                nameTitle = "Filtrar",
                icon = Filter(),
                //contentDescription = stringResource(R.string.content_filter_description),
                contentDescription = "dsadas",
                isVisible = true,
                onCLick = {events.goToDelete}
            ),
            Actions(
                //nameTitle = stringResource(R.string.list_acction_filter),
                nameTitle = "FiltrarDropDown",
                icon = Filter(),
                //contentDescription = stringResource(R.string.content_filter_description),
                contentDescription = "dsadas",
                isVisible = false,
                onCLick = {events.goToDelete}
            ),

        )
    )
}

