package com.dyddyd.aquariumwidget.feature.items

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dyddyd.aquariumwidget.core.data.repository.PartsRepository
import com.dyddyd.aquariumwidget.core.data.repository.RodRepository
import com.dyddyd.aquariumwidget.core.model.data.Parts
import com.dyddyd.aquariumwidget.core.model.data.Rod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val rodRepository: RodRepository,
    private val partsRepository: PartsRepository
) : ViewModel() {

    var selectedTab by  mutableIntStateOf(RODS_TAB)

    fun onTabSelected(index: Int) {
        selectedTab = index
    }

    val rodsUiState: StateFlow<RodsUiState> = combine(
        rodRepository.getAllRods(),
        rodRepository.getAllCollectedRods()
    ) { allRods, collectedRods ->
        RodsUiState.Success(
            allRods = allRods,
            collectedRods = collectedRods
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = RodsUiState.Loading
    )

    val partsUiState: StateFlow<PartsUiState> = combine(
        partsRepository.getAllParts(),
        partsRepository.getAllCollectedParts()
    ) { allParts, collectedParts ->
        PartsUiState.Success(
            allParts = allParts,
            collectedParts = collectedParts
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PartsUiState.Loading
    )
}

sealed interface RodsUiState {
    data object Loading : RodsUiState
    data class Success(
        val allRods: List<Rod>,
        val collectedRods: List<Rod>
    ) : RodsUiState
}

sealed interface PartsUiState {
    data object Loading : PartsUiState
    data class Success(
        val allParts: List<Parts>,
        val collectedParts: List<Parts>
    ) : PartsUiState
}


const val RODS_TAB = 0
const val PARTS_TAB = 1