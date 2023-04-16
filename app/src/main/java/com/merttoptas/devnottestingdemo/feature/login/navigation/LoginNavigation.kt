package com.merttoptas.devnottestingdemo.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.merttoptas.devnottestingdemo.feature.login.LoginScreenRoute

/**
 * Created by mertcantoptas on 14.04.2023
 */

const val loginNavigationRoute = "login_route"

fun NavController.navigateLoginScreen(navOptions: NavOptions? = null) {
    this.navigate(loginNavigationRoute, navOptions)
}

fun NavGraphBuilder.loginScreen() {
    composable(route = loginNavigationRoute) {
        LoginScreenRoute()
    }
}