package com.example.musicui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val viewModel: MainViewModel = viewModel()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Allow use to find out on which "Screen" we current are
    val controller = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val currentScreen = remember { viewModel.currentScreen.value }
    // change that to current screen's title
    val title = remember { mutableStateOf(currentScreen.title) }

    val isDialogOpen = remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        gesturesEnabled = !drawerState.isClosed,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .widthIn(max = 300.dp)
                    .fillMaxHeight(),
            ) {
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                ) {
                    items(screensInDrawer) { item ->
                        DrawerItem(
                            selected = currentRoute == item.drawerRoute,
                            item = item,
                            onDrawerItemClicked = {
                                scope.launch { drawerState.close() }
                                if (item.drawerRoute == Screen.DrawerScreen.AddAccount.drawerRoute) {
                                    // Open dialog
                                    isDialogOpen.value = true
                                } else {
                                    controller.navigate(route = item.drawerRoute)
                                    title.value = item.drawerTitle
                                }
                            },
                        )
                    }
                }
            }
        },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = title.value) },
                    navigationIcon = {
                    IconButton(
                        onClick = {
                            // Open the drawer
                            scope.launch { drawerState.open() }
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Menu"
                        )
                    }
                    },
                )
            },
        ) { paddingValues ->
            Navigation(
                viewModel = viewModel,
                navController = controller,
                paddingValues = paddingValues,
            )
            AccountDialog(isDialogOpen = isDialogOpen)
        }
    }
}

@Composable
fun DrawerItem(
    selected: Boolean,
    item: Screen.DrawerScreen,
    onDrawerItemClicked: () -> Unit,
) {
    val color = if (selected) Color.DarkGray else Color.White
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .background(color = color)
            .clickable { onDrawerItemClicked() }
    ) {
        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = item.drawerTitle,
            modifier = Modifier.padding(top = 4.dp, end = 8.dp)
        )
        Text(
            text = item.drawerTitle,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
fun Navigation(
    viewModel: MainViewModel,
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.DrawerScreen.AddAccount.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(Screen.DrawerScreen.Account.route) {
            AccountScreen()
        }
        composable(Screen.DrawerScreen.Subscription.route) {

        }
        composable(Screen.DrawerScreen.AddAccount.route) {

        }
    }
}