package com.github.ksalil.egghuntchecklist.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "egg_hunt_prefs")

class DataStoreEggHuntDataSource(
    private val context: Context
) : EggHuntDataSource {

    private object PreferencesKeys {
        val STRING_LIST_KEY = stringSetPreferencesKey("eggs_found")

    }

    override suspend fun saveFoundEggs(eggIds: Set<Int>) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.STRING_LIST_KEY] = eggIds
                .map { it.toString() }
                .toSet()
        }
    }

    override suspend fun getFoundEggs(): Set<Int> {
        return try {
            context.dataStore.data.map { preferences ->
                val stringSet = preferences[PreferencesKeys.STRING_LIST_KEY] ?: emptySet()
                stringSet
                    .mapNotNull { it.toIntOrNull() }
                    .toSet()
            }.first()
        } catch (e: Exception) {
            emptySet()
        }
    }

    override suspend fun clearFoundEggs() {
        context.dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.STRING_LIST_KEY)
        }
    }
}