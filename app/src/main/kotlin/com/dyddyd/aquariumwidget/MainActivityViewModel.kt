package com.dyddyd.aquariumwidget

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dyddyd.aquariumwidget.MainActivityUiState.Loading
import com.dyddyd.aquariumwidget.MainActivityUiState.Success
import com.dyddyd.aquariumwidget.core.data.repository.UserDataRepository
import com.dyddyd.aquariumwidget.core.data.repository.UserRepository
import com.dyddyd.aquariumwidget.core.model.data.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    userDataRepository: UserDataRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    val uiState: StateFlow<MainActivityUiState> = userDataRepository.userData.map {
        Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = Loading,
        started = SharingStarted.WhileSubscribed(5_000),

        )

    fun updateLastPlayedDate() {
        viewModelScope.launch {
            Log.d("MainActivityViewModel", "updateLastPlayedDate")
            userRepository.updateLastPlayedDate(
                curDate = SimpleDateFormat("yyyyMMdd").format(Date()).toInt()
            )
        }
    }
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val userData: UserData) : MainActivityUiState
}