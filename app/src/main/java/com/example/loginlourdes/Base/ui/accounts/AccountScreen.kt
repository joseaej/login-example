package com.example.loginlourdes.Base.ui.accounts

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.loginlourdes.Base.composable.Actions
import com.example.loginlourdes.Base.composable.BaseTopAppBar
import com.example.loginlourdes.Base.composable.BaseTopAppBarState
import com.example.loginlourdes.domain.data.model.account.Account
import com.example.loginlourdes.Base.ui.NoDataScreen
import com.example.loginlourdes.Base.ui.loading.LoadingScreen
import com.example.loginlourdes.utils.DeleteAccountDialog
import kotlinx.coroutines.launch

data class AccountListEvents(
    val openDrawer: () -> Unit,
    val goToCreation: () -> Unit,
    val goToDetails: (Account) -> Unit,
    val goToDelete: (Account) -> Unit,
    val shortList: () -> Unit,
    val onChangeIcon: @Composable () -> ImageVector
)

@Composable
fun AccountScreen(
    viewModel: AccountsListViewModel,
    openDrawer: () -> Unit,
    goToCreation: () -> Unit,
    goToDetails: (Account) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.getList()
    }

    val events = AccountListEvents(
        openDrawer = openDrawer,
        goToCreation = goToCreation,
        goToDetails = goToDetails,
        goToDelete = { account -> viewModel.delete(account) },
        shortList = viewModel::shortAccountList,
        onChangeIcon = {viewModel.onChangeIcon() }
    )

    Scaffold(
        topBar = { BaseTopAppBar(appBarState(events)) },
        floatingActionButton = {
            FloatingActionButton(onClick = events.goToCreation) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Icono ventana"
                )
            }
        }
    ) { innerPadding ->
        when (val state = viewModel.state) {
            AccountListState.NoData -> NoDataScreen()
            is AccountListState.Success -> AccountListContent(
                accounts = state.datalist,
                modifier = Modifier.padding(innerPadding),
                events = events
            )
            AccountListState.Loading -> LoadingScreen()
        }
    }
}

@Composable
fun AccountListContent(
    modifier: Modifier = Modifier,
    accounts: List<Account>,
    events: AccountListEvents
) {
    var accountDelete by remember { mutableStateOf<Account?>(null) }
    var accountDetail by remember { mutableStateOf<Account?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
            modifier = Modifier.padding(innerPadding)
        ) {
            items(accounts) { account ->
                AccountItem(
                    account = account,
                    goToDelete = { accountDelete = it },
                    goToDetails = { selectedAccount ->
                        accountDetail = selectedAccount
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Cuenta: ${accountDetail?.email}",
                                actionLabel = "Cerrar",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                )
            }
        }

        accountDelete?.let {
            DeleteAccountDialog(
                account = it,
                onConfirm = {
                    events.goToDelete(it)
                    accountDelete = null
                },
                onDismiss = { accountDelete = null }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountItem(
    modifier: Modifier = Modifier,
    account: Account,
    goToDelete: (Account) -> Unit,
    goToDetails: (Account) -> Unit
) {
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
                .combinedClickable(
                    onClick = { goToDetails(account) },
                    onLongClick = { goToDelete(account) }
                )
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
            Text(text = account.email.value, style = MaterialTheme.typography.titleMedium,)
        }
    }
}

@Composable
fun appBarState(events: AccountListEvents): BaseTopAppBarState {
    return BaseTopAppBarState(
        tittle = "Lista de Cuentas",
        upAction = {},
        icon = Icons.Filled.Menu,
        acctions = listOf(
            Actions(
                nameTitle = "Ordenar",
                icon = events.onChangeIcon(),
                contentDescription = "Ordenar alfabéticamente",
                isVisible = true,
                onCLick = {
                    events.shortList()
                }
            )
        )
    )
}
