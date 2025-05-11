package aung.thiha.photo.album.di

import org.koin.core.module.Module
import org.koin.core.qualifier.named

expect val sessionStorageModule: Module

internal const val PREF_AUTHENTICATION = "auth.preferences_pb"
val NAMED_AUTHENTICATION_PREFERENCE = named(PREF_AUTHENTICATION)