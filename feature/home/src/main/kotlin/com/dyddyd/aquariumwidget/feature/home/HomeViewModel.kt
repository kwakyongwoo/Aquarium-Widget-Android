package com.dyddyd.aquariumwidget.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dyddyd.aquariumwidget.core.data.repository.FishRepository
import com.dyddyd.aquariumwidget.core.data.repository.UserRepository
import com.dyddyd.aquariumwidget.core.model.data.Fish
import com.dyddyd.aquariumwidget.core.model.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    userRepository: UserRepository,
    fishRepository: FishRepository
) : ViewModel() {

    private val user: Flow<User> = userRepository.getUserInfo()

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

}

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(
        val aquariumThemeId: Int,
        val fishList: List<Fish>
    ) : HomeUiState
}