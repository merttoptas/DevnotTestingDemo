package com.merttoptas.devnottestingdemo.core.data.repository

import com.merttoptas.devnottestingdemo.core.data.model.LoginResponse
import kotlinx.coroutines.flow.Flow

/**
 * Created by mertcantoptas on 15.04.2023
 */

interface LoginRepository {
    suspend fun login(userName: String, password: String): Flow<LoginResponse>
}