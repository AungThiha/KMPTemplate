package aung.thiha.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

fun createKeyValueStorage(context: Context, prefFileName: String): DataStore<Preferences> = createKeyValueStorage(
    producePath = { context.filesDir.resolve(prefFileName).absolutePath }
)
