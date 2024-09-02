package com.dyddyd.aquariumwidget.feature.fishging

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dyddyd.aquariumwidget.core.data.repository.FishRepository
import com.dyddyd.aquariumwidget.core.data.repository.HabitatRepository
import com.dyddyd.aquariumwidget.core.data.repository.PartsRepository
import com.dyddyd.aquariumwidget.core.data.repository.QuestRepository
import com.dyddyd.aquariumwidget.core.data.repository.RodRepository
import com.dyddyd.aquariumwidget.core.data.repository.UserRepository
import com.dyddyd.aquariumwidget.core.database.model.Catch
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
    private val habitatRepository: HabitatRepository,
    private val rodRepository: RodRepository,
    private val fishRepository: FishRepository,
    private val questRepository: QuestRepository,
    private val partsRepository: PartsRepository
) : ViewModel() {

    private val user = userRepository.getUserInfo()

    private val habitat = user.flatMapLatest { user ->
        habitatRepository.getHabitatInfo(user.curHabitat)
    }

    private val rod = user.flatMapLatest { user ->
        rodRepository.getMatchedRod(user.maxHabitat)
    }

    private val allQuestsInHabitat = user.flatMapLatest { user ->
        questRepository.getAllQuestsInHabitat(user.maxHabitat)
    }

    private val allFishList = fishRepository.getAllFish()

    val fishingUiState: StateFlow<FishingUiState> = combine(
        habitat,
        rod,
        user
    ) { habitat, rod, user ->
        if (rod == null) {
            FishingUiState.Error
        } else {
            FishingUiState.Success(
                habitat = habitat,
                rod = rod,
                chance = user.chance,
                maxHabitat = user.maxHabitat
            )
        }
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

    fun fishing() {
        viewModelScope.launch {
            val rod = rod.first()
            val user = user.first()

            if (user.chance > 0) {
                fishingState = FishingState.Waiting(R.drawable.feature_fishing_state1)

                var rarity = selectRarity()
                var fish = allFishList.first()
                    .filter { it.rarity == rarity && it.habitatId == user.curHabitat }
                    .random()

                repeat(100) {
                    rarity = selectRarity()
                    fish = allFishList.first()
                        .filter { it.rarity == rarity && it.habitatId == user.curHabitat }
                        .random()
                    fishRepository.catchFish(fish.fishId, rod?.rodId ?: 1)
                }

//                userRepository.decreaseGameChanceCount()
                fishRepository.catchFish(fish.fishId, rod?.rodId ?: 1)

                delay(1000L)

                fishingState = FishingState.Bite(image = R.drawable.feature_fishing_state2)

                delay(1000L)

                fishingState = FishingState.Gotcha(image = R.drawable.feature_fishing_state3)

                delay(1000L)

                fishingState = FishingState.Caught(
                    fish = fish
                )
            }
        }
    }

    fun checkQuest() {
        viewModelScope.launch {
            val user = user.first()

            val questUncleared = allQuestsInHabitat.first().toMutableList()
            questUncleared.removeAll(
                questRepository.getAllClearQuestsInHabitat(user.maxHabitat).first()
            )

            val questCleared = mutableListOf<Quest>()

            questUncleared.forEach { q ->
                val isCleared = when (q) {
                    is QuestTypeTotal -> {
                        fishRepository.getAllCollectFish().first()
                            .count { it.habitatId == q.habitatId } >= q.targetCount
                    }

                    is QuestTypeSingle -> fishRepository.getCollectedFish(q.targetFishId)
                        .first().size >= q.targetCount

                    is QuestTypeTotalNoDup -> fishRepository.getAllCollectedFishWithoutDuplication()
                        .first().count { it.habitatId == q.habitatId } >= q.targetCount

                    is QuestTypeRarity -> fishRepository.getAllCollectedFishByRarity(q.rarity)
                        .first().count { it.habitatId == q.habitatId } >= q.targetCount

                    else -> {
                        Log.d("FishingViewModel", "quest type not matched")
                        false
                    }
                }

                if (isCleared) {
                    questCleared.add(q)
                    Log.d("FishingViewModel", "quest clear: ${q.title}")
                    questRepository.clearQuest(q.habitatId, q.questId)
                    partsRepository.collectPart(q.partsId)
                }
            }

            clearedQuestList = questCleared

            Log.d("FishingViewModel", "questUncleared: $clearedQuestList")

            questUncleared.removeAll(questCleared)
            if (questUncleared.isEmpty()) {
                userRepository.setCurrentHabitat(user.maxHabitat + 1)
                userRepository.clearCurrentHabitat(user.maxHabitat)
                isClearStage = true
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
        val rod: Rod,
        val chance: Int,
        val maxHabitat: Int
    ) : FishingUiState
}

sealed interface FishingState {

    data object NotFishing : FishingState

    data class Waiting(
        val image: Int
    ) : FishingState

    data class Bite(
        val image: Int
    ) : FishingState

    data class Gotcha(
        val image: Int
    ) : FishingState

    data class Caught(
        val fish: Fish
    ) : FishingState
}