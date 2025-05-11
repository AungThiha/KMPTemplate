package aung.thiha.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

fun createPrefDataStore(context: Context, prefFileName: String): DataStore<Preferences> = createPrefDataStore(
    producePath = { context.filesDir.resolve(prefFileName).absolutePath }
)
