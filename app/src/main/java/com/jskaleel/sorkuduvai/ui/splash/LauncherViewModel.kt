package com.jskaleel.sorkuduvai.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jskaleel.sorkuduvai.api.repo.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LauncherViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private var _isReady = MutableLiveData<Boolean>()
    var isReady: LiveData<Boolean> = _isReady


    fun delaySplashScreen() {
        viewModelScope.launch {
            delay(timeMillis = 2500)
            _isReady.postValue(true)
        }
    }
}