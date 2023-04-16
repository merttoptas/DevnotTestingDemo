package com.merttoptas.devnottestingdemo.core.data.util

import kotlinx.coroutines.flow.Flow

/**
 * Created by mertcantoptas on 14.04.2023
 */

interface NetworkMonitor {
    val isOnline: Flow<Boolean>
}
