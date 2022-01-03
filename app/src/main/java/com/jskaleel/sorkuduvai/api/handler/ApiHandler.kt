package com.jskaleel.sorkuduvai.api.handler

import com.jskaleel.sorkuduvai.api.services.ApiServices
import com.jskaleel.sorkuduvai.model.QueryResponse
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiHandler @Inject constructor(retrofit: Retrofit)  {
    private val apiServices: ApiServices by lazy {
        retrofit.create(
            ApiServices::class.java
        )
    }

    suspend fun query(word: String): Response<QueryResponse> {
        return apiServices.query(word)
    }
}