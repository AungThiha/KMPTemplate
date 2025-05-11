package aung.thiha.session.data

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import aung.thiha.session.domain.SessionStorage
import aung.thiha.session.domain.model.Session
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first

class SessionStorageImpl(
    private val dataStore: DataStore<Preferences>,
) : SessionStorage {
    private object PreferencesKeys {
        val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
        val USER_ID = stringPreferencesKey("USER_ID")
        val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")
    }

    override suspend fun getAuthenticationSession() : Session? {
        val preferences = dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                }
            }
            .first()

        val accessToken = preferences[PreferencesKeys.ACCESS_TOKEN]
        val refreshToken = preferences[PreferencesKeys.REFRESH_TOKEN]
        val userId = preferences[PreferencesKeys.USER_ID]
        return if (accessToken != null && refreshToken != null && userId != null) {
            Session(
                accessToken = accessToken,
                refreshToken = refreshToken,
                userId = userId,
            )
        } else {
            null
        }
    }

    override suspend fun setAuthenticationSession(session: Session?) {
        try {
            dataStore.edit { mutablePreferences ->
                session?.let { session ->
                    mutablePreferences[PreferencesKeys.ACCESS_TOKEN] = session.accessToken
                    mutablePreferences[PreferencesKeys.REFRESH_TOKEN] = session.refreshToken
                    mutablePreferences[PreferencesKeys.USER_ID] = session.userId
                } ?: run {
                    mutablePreferences.remove(PreferencesKeys.ACCESS_TOKEN)
                    mutablePreferences.remove(PreferencesKeys.REFRESH_TOKEN)
                    mutablePreferences.remove(PreferencesKeys.USER_ID)
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Logger.e(e.message ?: "no error message")
        }
    }
}