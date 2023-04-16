package com.merttoptas.devnottestingdemo.feature.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.merttoptas.devnottestingdemo.feature.login.navigation.loginNavigationRoute
import com.merttoptas.devnottestingdemo.feature.login.navigation.loginScreen

/**
 * Created by mertcantoptas on 14.04.2023
 */

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = loginNavigationRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        loginScreen()
    }
}