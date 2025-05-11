package aung.thiha.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

fun interface CreateKeyValueStorage {
    /**
     * Creates a key-value storage for a given scope.
     *
     * @param storageScope A string used to group related key-value pairs.
     * For example, pairs related to session storage are grouped under the "session" storage scope.
     */
    operator fun invoke(storageScope: String): DataStore<Preferences>
}
