package com.merttoptas.devnottestingdemo.core.data.repository.impl

import com.merttoptas.devnottestingdemo.core.data.model.LoginResponse
import com.merttoptas.devnottestingdemo.core.data.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by mertcantoptas on 15.04.2023
 */

class LoginRepositoryImpl @Inject constructor() : LoginRepository {
    override suspend fun login(userName: String, password: String) = flow {
        emit(LoginResponse("token"))
    }.flowOn(Dispatchers.IO)
}