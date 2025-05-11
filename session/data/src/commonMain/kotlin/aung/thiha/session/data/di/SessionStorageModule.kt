package aung.thiha.session.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import aung.thiha.session.data.SessionStorageImpl
import aung.thiha.session.domain.SessionStorage
import aung.thiha.storage.CreateKeyValueStorage
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val PREF_AUTHENTICATION = "auth.preferences_pb"
private val NAMED_AUTHENTICATION_PREFERENCE = named(PREF_AUTHENTICATION)

val sessionStorageModule = module {
    single<DataStore<Preferences>>(NAMED_AUTHENTICATION_PREFERENCE) {
        get<CreateKeyValueStorage>().invoke(PREF_AUTHENTICATION)
    }
    factory<SessionStorage> {
        SessionStorageImpl(
            dataStore = get(NAMED_AUTHENTICATION_PREFERENCE)
        )
    }
}