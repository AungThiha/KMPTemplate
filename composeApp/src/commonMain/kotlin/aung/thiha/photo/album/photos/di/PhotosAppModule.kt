package aung.thiha.photo.album.photos.di

import aung.thiha.photo.album.photos.domain.usecase.Signout as PhotosSignout
import aung.thiha.photo.album.authentication.domain.usecase.Signout as AuthenticationSignout
import org.koin.dsl.module

val PhotosAppModule = module {
    factory<PhotosSignout> {
        val delegate = get<AuthenticationSignout>()
        PhotosSignout { input ->
            delegate.invoke(input)
        }
    }
}
