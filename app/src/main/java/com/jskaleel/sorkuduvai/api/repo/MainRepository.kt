package com.jskaleel.sorkuduvai.api.repo

import com.google.gson.Gson
import com.jskaleel.sorkuduvai.api.handler.ApiHandler
import com.jskaleel.sorkuduvai.db.dao.RecentSearchDao
import com.jskaleel.sorkuduvai.db.entities.RecentSearchEntity
import com.jskaleel.sorkuduvai.model.QueryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val apiHandler: ApiHandler,
    private val recentSearchDao: RecentSearchDao
) {
    fun query(
        word: String?, onStart: () -> Unit,
        onCompletion: () -> Unit
    ): Flow<QueryResponse> = flow {
        if (word.isNullOrEmpty()) {
            emit(QueryResponse.QueryError("ta", "Invalid search term"))
            onCompletion()
        } else {
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

                        emit(successResponse)
                        recentSearchDao.insert(
                            RecentSearchEntity(
                                timeStamp = System.currentTimeMillis(),
                                word = word,
                                details = jsonBody.toString()
                            )
                        )
                        onCompletion()
                    } else {
                        emit(QueryResponse.QueryError("ta", status))
                        onCompletion()
                    }
                } else {
                    emit(QueryResponse.QueryError("ta", response.message() ?: "Unknown error"))
                    onCompletion()
                }
            } else {
                emit(QueryResponse.QueryError("ta", response.message() ?: "Unknown error"))
                onCompletion()
            }
        }
    }.onStart { onStart.invoke() }.flowOn(Dispatchers.IO)

    fun loadRecentSearch(
        onStart: () -> Unit,
        onCompletion: () -> Unit
    ) = flow {
        val recentSearch = recentSearchDao.getRecentSearch()
        recentSearch.collect { list ->
            emit(list)
            onCompletion.invoke()
        }
    }.onStart { onStart.invoke() }.flowOn(Dispatchers.IO)

    suspend fun removeRecent(timeStamp: Long) {
        recentSearchDao.deleteSearch(timeStamp)
    }
}