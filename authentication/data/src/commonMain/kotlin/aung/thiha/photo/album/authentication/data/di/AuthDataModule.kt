package aung.thiha.photo.album.authentication.data.di

import aung.thiha.photo.album.authentication.data.AuthenticationRepositoryImpl
import aung.thiha.photo.album.authentication.data.plugin.AuthPlugin
import aung.thiha.photo.album.authentication.data.remote.service.AuthenticationDataSource
import aung.thiha.photo.album.authentication.data.remote.service.AuthenticationService
import aung.thiha.photo.album.authentication.domain.AuthenticationRepository
import aung.thiha.photo.album.authentication.domain.usecase.Signout
import aung.thiha.session.domain.SessionStorage
import org.koin.dsl.module

val authenticationDataModule = module {
    factory<AuthPlugin> {
        AuthPlugin(
            sessionStorage = get<SessionStorage>(),
            authenticationServiceProvider = lazy(LazyThreadSafetyMode.NONE) {
                get<AuthenticationService>()
            },
            signoutProvider = lazy(LazyThreadSafetyMode.NONE) {
                get<Signout>()
            }
        )
    }
    factory<AuthenticationRepository> {
        AuthenticationRepositoryImpl(
            sessionStorage = get(),
            authenticationDataSourceProvider = lazy(LazyThreadSafetyMode.NONE) { get() },
        )
    }
    single<AuthenticationDataSource> {
        AuthenticationService(
            httpClient = get()
        )
    }
}
