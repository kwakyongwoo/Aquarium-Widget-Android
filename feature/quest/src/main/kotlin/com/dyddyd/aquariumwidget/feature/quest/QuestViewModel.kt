package com.dyddyd.aquariumwidget.feature.quest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dyddyd.aquariumwidget.core.data.repository.PartsRepository
import com.dyddyd.aquariumwidget.core.data.repository.QuestRepository
import com.dyddyd.aquariumwidget.core.data.repository.UserRepository
import com.dyddyd.aquariumwidget.core.model.data.Parts
import com.dyddyd.aquariumwidget.core.model.data.Quest
import com.dyddyd.aquariumwidget.core.model.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class QuestViewModel @Inject constructor(
    userRepository: UserRepository,
    private val questRepository: QuestRepository,
    partsRepository: PartsRepository,
) : ViewModel() {
    private val user: Flow<User> = userRepository.getUserInfo()

    private val allParts: Flow<List<Parts>> = partsRepository.getAllParts()

    val questUiState: StateFlow<QuestUiState> = user.flatMapLatest { user ->
        val habitatId = if (user.curHabitat < 5) user.curHabitat else 4

        combine(
            questRepository.getAllQuestsInHabitat(habitatId),
            questRepository.getAllClearQuestsInHabitat(habitatId),
            allParts
        ) { allQuestsInHabitat, allClearQuestsInHabitat, allParts ->
            QuestUiState.Success(
                habitatId = habitatId,
                allQuestsInHabitat = allQuestsInHabitat,
                allClearQuestsInHabitat = allClearQuestsInHabitat,
                allParts = allParts
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = QuestUiState.Loading
    )
}

sealed interface QuestUiState {
    data object Loading : QuestUiState
    data class Success(
        val habitatId: Int,
        val allQuestsInHabitat: List<Quest>,
        val allClearQuestsInHabitat: List<Quest>,
        val allParts: List<Parts>
    ) : QuestUiState
}