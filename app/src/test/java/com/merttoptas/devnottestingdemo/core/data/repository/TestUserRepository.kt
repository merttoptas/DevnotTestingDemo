package com.merttoptas.devnottestingdemo.core.data.repository

import com.merttoptas.devnottestingdemo.core.data.model.LoginResponse
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Created by mertcantoptas on 15.04.2023
 */
class TestLoginRepository : LoginRepository {

    private val loginFlow: MutableSharedFlow<LoginResponse> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override suspend fun login(userName: String, password: String) = loginFlow

    fun sendLogin(response: LoginResponse) {
        loginFlow.tryEmit(response)
    }
}