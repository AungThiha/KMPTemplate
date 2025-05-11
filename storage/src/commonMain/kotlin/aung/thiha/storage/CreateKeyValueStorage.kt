package aung.thiha.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

fun createKeyValueStorage(producePath: () -> String): DataStore<Preferences> =
        PreferenceDataStoreFactory.createWithPath(
            produceFile = { producePath().toPath() }
        )
