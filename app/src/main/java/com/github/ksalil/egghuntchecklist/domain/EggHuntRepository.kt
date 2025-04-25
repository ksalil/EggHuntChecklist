package com.github.ksalil.egghuntchecklist.domain

import com.github.ksalil.egghuntchecklist.data.EggHuntDataSource

class EggHuntRepository(
    private val dataSource: EggHuntDataSource,
    private val eggLocationList: List<EasterEgg> = com.github.ksalil.egghuntchecklist.data.eggLocationList
) {
    suspend fun saveFoundEggs(eggIds: Set<Int>) {
        dataSource.saveFoundEggs(eggIds)
    }

    suspend fun getFoundEggs(): Set<Int> {
        return dataSource.getFoundEggs()
    }

    suspend fun clearFoundEggs() {
        dataSource.clearFoundEggs()
    }

    fun getEggList(): List<EasterEgg> {
        return eggLocationList
    }
}