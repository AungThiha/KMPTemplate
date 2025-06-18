package aung.thiha.photo.album.di

import aung.thiha.photo.album.authentication.data.remote.service.AuthenticationDataSource
import aung.thiha.photo.album.authentication.data.remote.service.FakeAuthenticationDataSource
import aung.thiha.photo.album.di.core.fake
import org.koin.dsl.module

val authenticationDataModuleOverride = module {
    fake<AuthenticationDataSource, FakeAuthenticationDataSource> {
        FakeAuthenticationDataSource()
    }
}
