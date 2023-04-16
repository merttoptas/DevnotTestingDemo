package com.merttoptas.devnottestingdemo.feature.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.merttoptas.devnottestingdemo.feature.login.navigation.loginNavigationRoute
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by mertcantoptas on 16.04.2023
 */


class NavigationTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            MainNavHost(navController = navController)
        }
    }

    @Test
    fun appNavHost_verifyStartDestination() {
        val route = navController.currentDestination?.route
        assertEquals(route, loginNavigationRoute)
    }

    @Test
    fun appNavHost_HomeGoToLoginButton() {
        composeTestRule.onNodeWithTag("login_button").performClick()
        val route = navController.currentDestination?.route
        assertEquals(route, homeScreenRoute)
    }
}