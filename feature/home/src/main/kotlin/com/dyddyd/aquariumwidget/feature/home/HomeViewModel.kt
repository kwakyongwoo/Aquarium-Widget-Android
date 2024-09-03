package com.dyddyd.aquariumwidget.feature.home

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dyddyd.aquariumwidget.core.data.constant.FISH_ID
import com.dyddyd.aquariumwidget.core.data.repository.FishRepository
import com.dyddyd.aquariumwidget.core.data.repository.UserRepository
import com.dyddyd.aquariumwidget.core.model.data.Fish
import com.dyddyd.aquariumwidget.core.model.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    fishRepository: FishRepository
) : ViewModel() {

    private val user: Flow<User> = userRepository.getUserInfo()

//    init {
//        viewModelScope.launch {
//            fishRepository.addFishToAquarium(1, 11)
//            fishRepository.addFishToAquarium(1, 20)
//        }
//    }

    val homeUiState: StateFlow<HomeUiState> = user.flatMapLatest { user ->
        val aquariumThemeId = user.selectedAquariumThemeId ?: 1
        fishRepository.getAllFishInAquarium(aquariumThemeId)
            .map { fishList ->
                HomeUiState.Success(aquariumThemeId, fishList) as HomeUiState
            }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState.Loading
    )

    fun setAquariumThemeNext() = viewModelScope.launch {
        val user = user.first()

        val aquariumThemeId = user.selectedAquariumThemeId ?: 1

        if (aquariumThemeId < user.maxHabitat && aquariumThemeId < 4) {
            userRepository.changeAquariumTheme(aquariumThemeId + 1)
        }
    }

    fun setAquariumThemePrev() = viewModelScope.launch {
        val user = user.first()

        val aquariumThemeId = user.selectedAquariumThemeId ?: 1

        if (aquariumThemeId > 1) {
            userRepository.changeAquariumTheme(aquariumThemeId - 1)
        }
    }
}

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(
        val aquariumThemeId: Int,
        val fishList: List<Fish>
    ) : HomeUiState
}