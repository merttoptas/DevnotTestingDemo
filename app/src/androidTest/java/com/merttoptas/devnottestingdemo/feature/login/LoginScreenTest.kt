package com.merttoptas.devnottestingdemo.feature.login

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test
import com.merttoptas.devnottestingdemo.R

/**
 * Created by mertcantoptas on 16.04.2023
 */

class LoginScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()


    @Test
    fun loginScreen_whenScreenIsDisplayed_exists() {
        composeTestRule.setContent {
            LoginScreen(loginUiState = LoginUiState()) {}
        }
        composeTestRule.onNodeWithText(composeTestRule.activity.resources.getString(R.string.login_screen_title))
            .assertIsDisplayed()
    }


    @Test
    fun loginScreen_whenLoginButtonIsClicked_exists() {
        composeTestRule.setContent {
            LoginScreen(loginUiState = LoginUiState()) {}
        }
        composeTestRule.onNodeWithText(composeTestRule.activity.resources.getString(R.string.login_screen_title))
            .assertIsDisplayed()
    }

    @Test
    fun loginScreen_whenLoginButtonIsClicked_showLoading() {
        composeTestRule.setContent {
            LoginScreen(loginUiState = LoginUiState(isLoading = true)) {}
        }
        composeTestRule.onNodeWithTag("login_button").performClick()

        composeTestRule.onNodeWithTag("progress_bar")
            .assertIsDisplayed()
    }

    @Test
    fun loginScreen_whenLoginButtonIsClicked_showUsernameError() {
        composeTestRule.setContent {
            LoginScreen(
                loginUiState =
                LoginUiState(
                    isErrorUserName = true,
                    isLoading = false
                )
            ) {}
        }
        composeTestRule.onNodeWithTag("username_text_field")
            .performTextInput("")
        composeTestRule.onNodeWithTag("password_text_field").performTextInput("password")

        composeTestRule.onNodeWithTag("login_button").performClick()

        composeTestRule.onNodeWithTag("error_username_text", useUnmergedTree = true)
            .assertIsDisplayed()

        /*
        Bu kod, error_username_text etiketli bir view'in görüntülenip görüntülenmediğini doğru bir
         şekilde kontrol edecektir. useUnmergedFinders parametresinin true olarak ayarlanması,
         değişikliklerin henüz birleştirilmediği bir Compose UI ağacı kullanarak arama yapılmasını sağlayacaktır.
         */
    }

    @Test
    fun loginScreen_whenLoginButtonIsClicked_showPasswordError() {
        composeTestRule.setContent {
            LoginScreen(
                loginUiState =
                LoginUiState(
                    isErrorPassword = true,
                    isLoading = false
                )
            ) {}
        }
        composeTestRule.onNodeWithTag("username_text_field")
            .performTextInput("username")
        composeTestRule.onNodeWithTag("password_text_field").performTextInput("")

        composeTestRule.onNodeWithTag("login_button").performClick()

        composeTestRule.onNodeWithTag("error_password_text", useUnmergedTree = true)
            .assertIsDisplayed()

        /*
        Bu kod, error_password_text etiketli bir view'in görüntülenip görüntülenmediğini doğru bir
         şekilde kontrol edecektir. useUnmergedFinders parametresinin true olarak ayarlanması,
         değişikliklerin henüz birleştirilmediği bir Compose UI ağacı kullanarak arama yapılmasını sağlayacaktır.
         */
    }

}