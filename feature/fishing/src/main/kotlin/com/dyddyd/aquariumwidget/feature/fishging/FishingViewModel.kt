package com.dyddyd.aquariumwidget.feature.fishging

import androidx.lifecycle.ViewModel
import com.dyddyd.aquariumwidget.core.data.repository.HabitatRepository
import com.dyddyd.aquariumwidget.core.data.repository.RodRepository
import com.dyddyd.aquariumwidget.core.data.repository.UserRepository
import com.dyddyd.aquariumwidget.core.model.data.Fish
import com.dyddyd.aquariumwidget.core.model.data.Habitat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class FishingViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val habitatRepository: HabitatRepository,
    private val rodRepository: RodRepository,
) : ViewModel() {

    private val user = userRepository.getUserInfo()

}

sealed interface FishingUiState {
    data object Loading : FishingUiState
    data object Error : FishingUiState
    data class Success(
        val habitat: Habitat,
        val fishList: List<Fish>,
    ) : FishingUiState
}