package com.jskaleel.sorkuduvai.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jskaleel.sorkuduvai.api.repo.ApiRepository
import com.jskaleel.sorkuduvai.utils.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) : ViewModel() {

    fun queryWord(word: String) {
        viewModelScope.launch(Dispatchers.IO) {
            apiRepository.query(word).collect {
                when (it) {
                    is NetworkResponse.Success -> {
                    }
                }
            }
        }
    }
}