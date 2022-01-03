package com.jskaleel.sorkuduvai.api.repo

import com.jskaleel.sorkuduvai.api.handler.ApiHandler
import com.jskaleel.sorkuduvai.model.QueryResponse
import com.jskaleel.sorkuduvai.utils.network.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepository @Inject constructor(private val apiHandler: ApiHandler) {

    suspend fun query(word: String): Flow<NetworkResponse<QueryResponse>> = flow {
        emit(NetworkResponse.Loading)
        val response = apiHandler.query(word)
        if (response.isSuccessful) {
            emit(NetworkResponse.Success(response.body()))
        } else {
            emit(NetworkResponse.Error(response.message() ?: "Unknown error"))
        }
    }
}