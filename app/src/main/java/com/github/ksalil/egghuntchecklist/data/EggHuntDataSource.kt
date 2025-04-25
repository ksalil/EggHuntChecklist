package com.github.ksalil.egghuntchecklist.data

interface EggHuntDataSource {
    suspend fun saveFoundEggs(eggIds: Set<Int>)
    suspend fun getFoundEggs(): Set<Int>
    suspend fun clearFoundEggs()
}