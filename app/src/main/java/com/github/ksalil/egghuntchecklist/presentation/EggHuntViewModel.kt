package com.github.ksalil.egghuntchecklist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ksalil.egghuntchecklist.data.eggLocationList
import com.github.ksalil.egghuntchecklist.presentation.mvi.EggHuntAction
import com.github.ksalil.egghuntchecklist.presentation.mvi.EggHuntEffect
import com.github.ksalil.egghuntchecklist.presentation.mvi.EggHuntState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EggHuntViewModel : ViewModel() {

    private val _state = MutableStateFlow(EggHuntState(eggList = eggLocationList))
    val state = _state.asStateFlow()

    private val _effect = Channel<EggHuntEffect>()
    val effect = _effect.receiveAsFlow()

    fun onAction(action: EggHuntAction) {
        when (action) {
            is EggHuntAction.OnEggTapped -> {
                handleOnEggTapped(action.eggId)
            }

            is EggHuntAction.OnDialogDismissed -> {
                handleOnEggProgressDismissed()
            }
        }
    }

    private fun handleOnEggProgressDismissed() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    currentEgg = null,
                )
            }
        }
    }

    private fun handleOnEggTapped(eggId: Int) {
        viewModelScope.launch {
            val egg = eggLocationList.find { it.id == eggId } ?: return@launch
            val updatedEggIds = _state.value.foundEggs.toMutableSet()
            if (updatedEggIds.contains(eggId)) {
                updatedEggIds.remove(eggId)
            } else {
                updatedEggIds.add(eggId)
            }
            _state.update {
                it.copy(
                    currentEgg = egg,
                    foundEggs = updatedEggIds
                )
            }
            _effect.send(EggHuntEffect.OnShowDialog)
        }
    }
}