package com.example.musicui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.NavigationDrawerItem
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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Allow use to find out on which "Screen" we current are
    val controller = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // change that to current screen's title
    val title = remember { mutableStateOf("") }

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
                        NavigationDrawerItem(
                            label = { Text(text = item.drawerTitle) },
                            selected = currentRoute == item.drawerRoute,
                            onClick = { scope.launch { drawerState.close() } },
                        )
                        if (item.drawerRoute == Screen.DrawerScreen.AddAccount.drawerRoute) {
                            // Open dialog
                        } else {
                            controller.navigate(route = item.drawerRoute)
                            title.value = item.drawerTitle
                        }
                    }
                }
            }
        },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "Home") }, navigationIcon = {
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
                })
            },
        ) { paddingValues ->
            Text(
                text = "Text", modifier = Modifier.padding(paddingValues)
            )
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