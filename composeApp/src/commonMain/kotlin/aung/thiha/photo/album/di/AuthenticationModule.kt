package aung.thiha.photo.album.di

import aung.thiha.photo.album.authentication.data.AuthenticationRepositoryImpl
import aung.thiha.photo.album.authentication.domain.AuthenticationRepository
import aung.thiha.photo.album.storage.NAMED_AUTHENTICATION_PREFERENCE
import org.koin.dsl.module

val authenticationModule = module {
    factory<AuthenticationRepository> {
        AuthenticationRepositoryImpl(dataStore = get(NAMED_AUTHENTICATION_PREFERENCE))
    }
}