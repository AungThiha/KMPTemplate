package aung.thiha.photo.album.authentication.data

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.*
import aung.thiha.photo.album.authentication.data.model.AuthenticationToken
import aung.thiha.photo.album.authentication.domain.AuthenticationRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first

class AuthenticationRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : AuthenticationRepository {
    private object PreferencesKeys {
        val AUTHENTICATION_TOKEN = stringPreferencesKey("AUTHENTICATION_TOKEN")
    }

    override suspend fun getAuthenticationToken() : AuthenticationToken? {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                }
            }
            .first()[PreferencesKeys.AUTHENTICATION_TOKEN]
                ?.let { AuthenticationToken(it) }
    }

    override suspend fun setAuthenticationToken(authenticationToken: AuthenticationToken?) {
        dataStore.edit { mutablePreferences ->
            authenticationToken?.token?.let { token ->
                mutablePreferences[PreferencesKeys.AUTHENTICATION_TOKEN] = token
            } ?: run {
                mutablePreferences.remove(PreferencesKeys.AUTHENTICATION_TOKEN)
            }
        }
    }

}