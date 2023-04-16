package com.merttoptas.devnottestingdemo.core.data.di

import com.merttoptas.devnottestingdemo.core.data.util.ConnectivityManagerNetworkMonitor
import com.merttoptas.devnottestingdemo.core.data.util.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by mertcantoptas on 14.04.2023
 */

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor
    ): NetworkMonitor
}