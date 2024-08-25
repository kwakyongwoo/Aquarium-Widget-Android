package com.dyddyd.aquariumwidget.feature.fish

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.dyddyd.aquariumwidget.core.data.constant.FISH_ID
import com.dyddyd.aquariumwidget.core.data.repository.FishRepository
import com.dyddyd.aquariumwidget.core.data.repository.HabitatRepository
import com.dyddyd.aquariumwidget.core.data.repository.UserRepository
import com.dyddyd.aquariumwidget.core.model.data.Aquarium
import com.dyddyd.aquariumwidget.core.model.data.Fish
import com.dyddyd.aquariumwidget.core.model.data.User
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FishDialogViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
    private val fishRepository: FishRepository,
    private val habitatRepository: HabitatRepository
) : ViewModel() {
    private val fishList: Flow<List<Fish>> = userRepository.getUserInfo().flatMapLatest { user ->
        fishRepository.getAllFishInAquarium(user.selectedAquariumThemeId ?: 1)
    }

    private val fishId: Flow<Int> = savedStateHandle.getStateFlow(FISH_ID, 0)

    val fishDialogUiState: StateFlow<FishDialogUiState> = fishId.flatMapLatest { id ->
        if (id == 0) {
            flowOf(FishDialogUiState.Error)
        } else {
            fishRepository.getFishById(id).flatMapLatest { fish ->
                habitatRepository.getHabitatInfo(fish.habitatId).combine(fishList) { habitat, fishList ->
                    FishDialogUiState.Success(fish, fishList.any { it.fishId == id }, habitat.name)
                }
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = FishDialogUiState.Loading
    )

    fun setFishId(fishId: Int) {
        savedStateHandle[FISH_ID] = fishId
    }

    fun addRemoveFishInAquarium(isInAquarium: Boolean) {
        viewModelScope.launch {
            val user: User = userRepository.getUserInfo().first()

            if (isInAquarium) {
                fishRepository.removeFishFromAquarium(user.selectedAquariumThemeId ?: 1, fishId.first())
            }
            else {
                fishRepository.addFishToAquarium(user.selectedAquariumThemeId ?: 1, fishId.first())
            }
        }
    }
}

sealed interface FishDialogUiState {
    data object Loading : FishDialogUiState
    data object Error : FishDialogUiState
    data class Success(
        val fish: Fish,
        val isInAquarium: Boolean,
        val habitatName: String,
    ) : FishDialogUiState
}