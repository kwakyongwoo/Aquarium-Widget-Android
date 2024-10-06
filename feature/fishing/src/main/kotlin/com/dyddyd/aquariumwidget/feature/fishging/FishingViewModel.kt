package com.dyddyd.aquariumwidget.feature.fishging

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dyddyd.aquariumwidget.core.data.repository.FishRepository
import com.dyddyd.aquariumwidget.core.data.repository.HabitatRepository
import com.dyddyd.aquariumwidget.core.data.repository.PartsRepository
import com.dyddyd.aquariumwidget.core.data.repository.QuestRepository
import com.dyddyd.aquariumwidget.core.data.repository.RodRepository
import com.dyddyd.aquariumwidget.core.data.repository.UserRepository
import com.dyddyd.aquariumwidget.core.model.data.Fish
import com.dyddyd.aquariumwidget.core.model.data.Habitat
import com.dyddyd.aquariumwidget.core.model.data.Quest
import com.dyddyd.aquariumwidget.core.model.data.QuestTypeRarity
import com.dyddyd.aquariumwidget.core.model.data.QuestTypeSingle
import com.dyddyd.aquariumwidget.core.model.data.QuestTypeTotal
import com.dyddyd.aquariumwidget.core.model.data.QuestTypeTotalNoDup
import com.dyddyd.aquariumwidget.core.model.data.Rod
import com.dyddyd.aquariumwidget.feature.fishing.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class FishingViewModel @Inject constructor(
    private val userRepository: UserRepository,
    habitatRepository: HabitatRepository,
    private val fishRepository: FishRepository,
    private val questRepository: QuestRepository,
    private val partsRepository: PartsRepository,
    private val rodRepository: RodRepository
) : ViewModel() {

    private val user = userRepository.getUserInfo()

    private val habitat = user.flatMapLatest { user ->
        habitatRepository.getHabitatInfo(user.curHabitat)
    }

    private val allQuestsInHabitat = user.flatMapLatest { user ->
        questRepository.getAllQuestsInHabitat(user.curHabitat)
    }

    private val allFishList = fishRepository.getAllFish()

    val fishingUiState: StateFlow<FishingUiState> = combine(
        habitat,
        user
    ) { habitat, user ->
        FishingUiState.Success(
            habitat = habitat,
            chance = user.chance,
            maxHabitat = user.maxHabitat
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = FishingUiState.Loading
    )

    var fishingState: FishingState by mutableStateOf(FishingState.NotFishing)

    var clearedQuestList: List<Quest>? by mutableStateOf(null)
    var isClearStage by mutableStateOf(false)

    fun checkAndResetHearts() {
        viewModelScope.launch {
            user.map { user ->
                if (user.lastPlayedDate != SimpleDateFormat("yyyyMMdd").format(Date()).toInt()) {
                    userRepository.resetGameChanceCount()
                }
            }
        }
    }

    private fun selectRarity(): String {
        val rarityList =
            mapOf("Common" to 50, "Uncommon" to 40, "Rare" to 30, "Epic" to 15, "Legendary" to 10)

        val randomNumber = Random.nextInt(1, rarityList.values.sum() + 1)
        var currentWeight = 0

        for ((rarity, weight) in rarityList) {
            currentWeight += weight
            if (randomNumber <= currentWeight) {
                return rarity
            }
        }
        return "Common"
    }

    private val TIME_INTERVAL = 700L

    fun fishing() {
        viewModelScope.launch {
            userRepository.decreaseGameChanceCount()

            val user = user.first()

            if (user.chance > 0) {
                fishingState = FishingState.Waiting(R.drawable.feature_fishing_state1)

                var rarity = selectRarity()
                var fish = allFishList.first()
                    .filter { it.rarity == rarity && it.habitatId == user.curHabitat }
                    .random()

                // debug
//                repeat(100) {
//
//                    rarity = selectRarity()
//                    fish = allFishList.first()
//                        .filter { it.rarity == rarity && it.habitatId == user.curHabitat }
//                        .random()
//                    fishRepository.catchFish(fish.fishId, user.maxHabitat)
//                }

                fishRepository.catchFish(fish.fishId, user.maxHabitat)

                delay(TIME_INTERVAL)

                fishingState = FishingState.Bite(image = R.drawable.feature_fishing_state2)

                delay(TIME_INTERVAL)

                fishingState = FishingState.Gotcha(image = R.drawable.feature_fishing_state3)

                delay(TIME_INTERVAL)

                fishingState = FishingState.Caught(
                    fish = fish
                )
            }
        }
    }

    fun checkQuest() {
        viewModelScope.launch {
            val user = user.first()

            if (user.curHabitat != user.maxHabitat) {
                return@launch
            }

            val questUncleared = allQuestsInHabitat.first().toMutableList()
            questUncleared.removeAll(
                questRepository.getAllClearQuestsInHabitat(user.maxHabitat).first()
            )

            val questCleared = mutableListOf<Quest>()

            questUncleared.forEach { q ->
                if (q.actualCount >= q.targetCount) {
                    questCleared.add(q)
                    questRepository.clearQuest(q.habitatId, q.questId)
                    partsRepository.collectPart(q.partsId)
                }
            }

            clearedQuestList = questCleared

            questUncleared.removeAll(questCleared)
            if (questUncleared.isEmpty()) {
                userRepository.clearCurrentHabitat(user.maxHabitat)
                rodRepository.collectRod(user.curHabitat + 1)
                if (user.maxHabitat < 4) {
                    userRepository.setCurrentHabitat(user.curHabitat + 1)
                    isClearStage = true
                }
            }
        }
    }

    fun changeStage(stageId: Int) {
        viewModelScope.launch {
            userRepository.setCurrentHabitat(stageId)
        }
    }
}

sealed interface FishingUiState {
    data object Loading : FishingUiState
    data object Error : FishingUiState
    data class Success(
        val habitat: Habitat,
        val chance: Int,
        val maxHabitat: Int
    ) : FishingUiState
}

sealed interface FishingState {
    data object NotFishing : FishingState
    data class Waiting(val image: Int) : FishingState
    data class Bite(val image: Int) : FishingState
    data class Gotcha(val image: Int) : FishingState
    data class Caught(val fish: Fish) : FishingState
}