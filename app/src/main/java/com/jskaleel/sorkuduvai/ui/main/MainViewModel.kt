package com.jskaleel.sorkuduvai.ui.main

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jskaleel.sorkuduvai.R
import com.jskaleel.sorkuduvai.api.repo.MainRepository
import com.jskaleel.sorkuduvai.db.entities.RecentSearchEntity
import com.jskaleel.sorkuduvai.model.QueryResponse
import com.jskaleel.sorkuduvai.utils.extensions.isOnline
import com.jskaleel.sorkuduvai.utils.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _message: MutableLiveData<String> = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _loading: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading


    private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val _queryResponse: MutableLiveData<QueryResponse?> = MutableLiveData<QueryResponse?>()
    val queryResponse: LiveData<QueryResponse?> = _queryResponse

    val recentSearchList: Flow<List<RecentSearchEntity>> =
        mainRepository.loadRecentSearch(
            onStart = { _isLoading.value = true },
            onCompletion = { _isLoading.value = false }
        )

    fun queryWord(context: Context, word: String) {
        if (!context.isOnline()) {
            _message.value = context.getString(R.string.no_internet_connect)
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                mainRepository.query(word).collect {
                    when (it) {
                        is NetworkResponse.Success -> {
                            _loading.postValue(false)
                            _queryResponse.postValue(it.data)
                        }
                        is NetworkResponse.Error -> {
                            _loading.postValue(false)
                            _message.postValue(it.message)
                        }
                        NetworkResponse.Loading -> {
                            _loading.postValue(true)
                        }
                    }
                }
            }
        }
    }
}