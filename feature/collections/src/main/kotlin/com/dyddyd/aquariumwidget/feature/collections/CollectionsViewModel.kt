package com.dyddyd.aquariumwidget.feature.collections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dyddyd.aquariumwidget.core.data.repository.FishRepository
import com.dyddyd.aquariumwidget.core.model.data.Fish
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel @Inject constructor(
    fishRepository: FishRepository,
) : ViewModel() {
    val collectionsUiState = fishRepository.getAllFish().combine(
        fishRepository.getAllCollectFish()
    ) { allFishList, collectedFishList ->
        CollectionsUiState.Success(
            allFishList = allFishList,
            collectedFishList = collectedFishList
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CollectionsUiState.Loading
    )

}

sealed class CollectionsUiState {
    data object Loading : CollectionsUiState()
    data class Success(
        val allFishList: List<Fish>,
        val collectedFishList: List<Fish>
    ) : CollectionsUiState()
}