package com.github.ksalil.egghuntchecklist.presentation.mvi

import com.github.ksalil.egghuntchecklist.domain.EasterEgg

data class EggHuntState(
    val eggList: List<EasterEgg>,
    val foundEggs:Set<Int> = emptySet(),
    val currentEgg: EasterEgg? = null,
    val showEggDialog: Boolean = false
)
