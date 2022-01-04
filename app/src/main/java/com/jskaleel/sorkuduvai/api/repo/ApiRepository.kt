package com.jskaleel.sorkuduvai.api.repo

import com.google.gson.Gson
import com.jskaleel.sorkuduvai.api.handler.ApiHandler
import com.jskaleel.sorkuduvai.model.QueryResponse
import com.jskaleel.sorkuduvai.utils.network.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepository @Inject constructor(private val apiHandler: ApiHandler) {

    suspend fun query(word: String): Flow<NetworkResponse<QueryResponse>> = flow {
        emit(NetworkResponse.Loading)
        val response = apiHandler.query(word)
        if (response.isSuccessful) {
            val responseStr = response.body()?.string()
            if (responseStr != null) {
                val jsonBody = JSONObject(responseStr)

                val status: String = jsonBody.optString("status", "No match found")
                if (status.equals("success", true)) {
                    val successResponse =
                        Gson().fromJson(
                            jsonBody.toString(),
                            QueryResponse.QuerySuccess::class.java
                        )
                    emit(NetworkResponse.Success(successResponse))
                } else {
                    emit(NetworkResponse.Error(status))
                }
            } else {
                emit(NetworkResponse.Error(response.message() ?: "Unknown error"))
            }
        } else {
            emit(NetworkResponse.Error(response.message() ?: "Unknown error"))
        }
    }
}