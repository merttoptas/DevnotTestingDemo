package com.merttoptas.devnottestingdemo.feature.appstate

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.merttoptas.devnottestingdemo.R
import com.merttoptas.devnottestingdemo.core.data.util.NetworkMonitor
import com.merttoptas.devnottestingdemo.feature.navigation.MainNavHost
import com.merttoptas.devnottestingdemo.ui.components.MainAppScaffold

/**
 * Created by mertcantoptas on 14.04.2023
 */

@OptIn(
    ExperimentalComposeUiApi::class
)
@Composable
fun MainApp(
    networkMonitor: NetworkMonitor,
    modifier: Modifier = Modifier,
    appState: MainAppState = rememberMainAppState(
        networkMonitor = networkMonitor,
    ),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()

    val notConnectedMessage = stringResource(R.string.not_network_connected)
    LaunchedEffect(isOffline) {
        if (isOffline) snackbarHostState.showSnackbar(
            message = notConnectedMessage,
            duration = SnackbarDuration.Indefinite
        )
    }

    MainAppScaffold(
        modifier = modifier.semantics {
            testTagsAsResourceId = true
        },
        backgroundColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(snackbarHostState) },

        ) {
        MainNavHost(
            navController = appState.navController,
        )
    }
}
