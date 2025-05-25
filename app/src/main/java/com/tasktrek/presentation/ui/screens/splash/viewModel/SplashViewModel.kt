package com.tasktrek.presentation.ui.screens.splash.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val _isLoadingDone = MutableStateFlow(false)
    val isLoadingDone: StateFlow<Boolean> = _isLoadingDone

    init {
        viewModelScope.launch {

            TODO("Check if user is logged in and load list of tasks and projects")

            delay(2000)
            _isLoadingDone.value = true
        }
    }
}
