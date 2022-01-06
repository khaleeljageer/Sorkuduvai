package com.jskaleel.sorkuduvai.ui.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jskaleel.sorkuduvai.api.repo.MainRepository
import com.jskaleel.sorkuduvai.db.entities.RecentSearchEntity
import com.jskaleel.sorkuduvai.model.QueryResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    private val _loading: MutableState<Boolean> = mutableStateOf(false)
    val loading: State<Boolean> get() = _loading

    private val _recentLoading: MutableState<Boolean> = mutableStateOf(false)
    val recentLoading: State<Boolean> get() = _recentLoading

    val recentSearchList: Flow<List<RecentSearchEntity>> =
        mainRepository.loadRecentSearch(
            onStart = { _recentLoading.value = true },
            onCompletion = { _recentLoading.value = false }
        )

    fun queryWord(word: String?): Flow<QueryResponse> =
        mainRepository.query(word, onStart = {
            _loading.value = true
        }, onCompletion = {
            _loading.value = false
        })

    fun removeRecentItem(timeStamp: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.removeRecent(timeStamp)
        }
    }
}