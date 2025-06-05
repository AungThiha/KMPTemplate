package aung.thiha.photo.album.photos.domain.usecase

import aung.thiha.operation.SuspendOperation

/**
 * Actual signout logic is inside authentication but
 * feature modules shouldn't cross-reference each other to maintain modular integrity
 * So, the adapter for this is written in [PhotosAppModule.kt] in ComposeApp module
* */
fun interface Signout : SuspendOperation<Unit, Unit>