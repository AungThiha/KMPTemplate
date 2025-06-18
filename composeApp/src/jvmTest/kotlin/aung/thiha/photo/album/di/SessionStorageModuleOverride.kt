package aung.thiha.photo.album.di

import aung.thiha.photo.album.di.core.fake
import aung.thiha.session.domain.FakeSessionStorage
import aung.thiha.session.domain.SessionStorage
import org.koin.dsl.module

val sessionStorageModule = module {
    fake<SessionStorage, FakeSessionStorage> {
        FakeSessionStorage()
    }
}

