package aung.thiha.photo.album.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath
import org.koin.core.qualifier.named

fun createDataStore(producePath: () -> String): DataStore<Preferences> =
        PreferenceDataStoreFactory.createWithPath(
            produceFile = { producePath().toPath() }
        )

internal const val PREF_AUTHENTICATION = "auth.preferences_pb"
val NAMED_AUTHENTICATION_PREFERENCE = named(PREF_AUTHENTICATION)