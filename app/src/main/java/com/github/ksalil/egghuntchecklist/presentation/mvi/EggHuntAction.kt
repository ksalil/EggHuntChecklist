package com.github.ksalil.egghuntchecklist.presentation.mvi

sealed interface EggHuntAction {
    data class OnEggTapped(val eggId: Int): EggHuntAction
    data object OnDialogDismissed: EggHuntAction
}