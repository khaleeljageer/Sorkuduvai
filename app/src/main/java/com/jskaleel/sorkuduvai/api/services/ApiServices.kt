package com.jskaleel.sorkuduvai.api.services

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("/sorkuvai")
    suspend fun query(
        @Query("q") query: String
    ): Response<ResponseBody>
}