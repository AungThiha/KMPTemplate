package aung.thiha.photo.album.photos.presentation.overview

import aung.thiha.photo.album.photos.domain.model.Photo

sealed class PhotoListState {
    data class Content(val photos: List<Photo>) : PhotoListState()
    data object Loading : PhotoListState()
    data object LoadingFailed : PhotoListState()
}