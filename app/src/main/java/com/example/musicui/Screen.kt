package com.example.musicui

import androidx.annotation.DrawableRes

sealed class Screen(
    val title: String,
    val route: String,
) {
    sealed class DrawerScreen(
        val drawerTitle: String,
        val drawerRoute: String,
        @DrawableRes val icon: Int,
    ) : Screen(drawerTitle, drawerRoute) {
        data object Account : DrawerScreen(
            drawerTitle = "Account",
            drawerRoute = "account",
            icon = R.drawable.account,
        )

        data object Subscription : DrawerScreen(
            drawerTitle = "Subscription",
            drawerRoute = "subscription",
            icon = R.drawable.subscribe,
        )

        data object AddAccount : DrawerScreen(
            drawerTitle = "Add Account",
            drawerRoute = "add_account",
            icon = R.drawable.person_add_alt_1_24,
        )
    }
}

val screensInDrawer = listOf(
    Screen.DrawerScreen.Account,
    Screen.DrawerScreen.Subscription,
    Screen.DrawerScreen.AddAccount,
)