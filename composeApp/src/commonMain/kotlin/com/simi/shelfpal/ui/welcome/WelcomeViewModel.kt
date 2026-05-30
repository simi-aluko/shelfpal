package com.simi.shelfpal.ui.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WelcomeViewModel : ViewModel() {

    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        object Success : UiState()
        object Error : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun signInAnonymously() {
        if (_uiState.value == UiState.Loading) return
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                Firebase.auth.signInAnonymously()
                _uiState.value = UiState.Success
            } catch (e: Exception) {
                _uiState.value = UiState.Error
            }
        }
    }

    fun clearError() {
        _uiState.value = UiState.Idle
    }
}
