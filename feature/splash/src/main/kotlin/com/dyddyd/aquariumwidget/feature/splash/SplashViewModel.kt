package com.dyddyd.aquariumwidget.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import com.dyddyd.aquariumwidget.feature.splash.SplashUiState.Finished

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    val splashUiState: StateFlow<SplashUiState> = flow {
        delay(2000L)
        emit(Finished)
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SplashUiState.Loading
        )

}

sealed interface SplashUiState {
    data object Loading : SplashUiState
    data object Finished : SplashUiState
}