package com.jskaleel.sorkuduvai.utils.network


sealed class NetworkResponse<out T> {
    object Loading : NetworkResponse<Nothing>()
    data class Success<out T>(val data: T?) : NetworkResponse<T>()
    data class Error<out T>(val message: String) : NetworkResponse<T>()
}
