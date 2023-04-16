package com.merttoptas.devnottestingdemo.core.data.di

import com.merttoptas.devnottestingdemo.core.data.repository.LoginRepository
import com.merttoptas.devnottestingdemo.core.data.repository.impl.LoginRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Created by mertcantoptas on 15.04.2023
 */

@Module
@InstallIn(ViewModelComponent::class)
class LoginModule {
    @Provides
    fun provideLoginRepository(): LoginRepository {
        return LoginRepositoryImpl()
    }
}