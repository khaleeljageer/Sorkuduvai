package com.jskaleel.sorkuduvai.ui.main

import android.content.Context
import android.widget.Toast
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

    fun queryWord(context: Context, word: String) {
        viewModelScope.launch(Dispatchers.IO) {
            apiRepository.query(word).collect {
                when (it) {
                    is NetworkResponse.Success -> {
                        viewModelScope.launch(Dispatchers.Main) {
                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                        }
                    }
                    is NetworkResponse.Error -> {
                        viewModelScope.launch(Dispatchers.Main) {
                            Toast.makeText(context, "Error : ${it.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    NetworkResponse.Loading -> {

                    }
                }
            }
        }
    }
}