package com.github.ksalil.egghuntchecklist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.ksalil.egghuntchecklist.domain.EggHuntRepository

class EggHuntViewModelFactory(
    private val repository: EggHuntRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EggHuntViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EggHuntViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}