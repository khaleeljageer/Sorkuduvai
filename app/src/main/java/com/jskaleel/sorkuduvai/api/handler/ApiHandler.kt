package com.jskaleel.sorkuduvai.api.handler

import com.jskaleel.sorkuduvai.api.services.ApiServices
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiHandler @Inject constructor(retrofit: Retrofit) {
    private val apiServices: ApiServices by lazy {
        retrofit.create(
            ApiServices::class.java
        )
    }

    suspend fun query(word: String): Response<ResponseBody> {
        return try {
            apiServices.query(word)
        } catch (e: Exception) {
            val message = e.message ?: "Unknown Exception"
            Response.error(499, message.toResponseBody())
        }
    }
}