package aung.thiha.photo.album.photos.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aung.thiha.photo.album.coroutines.AppDispatchers
import aung.thiha.photo.album.operation.Outcome
import aung.thiha.photo.album.operation.SuspendOperation
import aung.thiha.photo.album.photos.domain.model.Photo
import kotlinx.coroutines.launch

class PhotoListViewModel(
    private val _signout: suspend () -> Unit,
    private val photos: SuspendOperation<Unit, List<Photo>>
) : ViewModel() {

    private val _photoListState = mutableStateOf<PhotoListState>(PhotoListState.Loading)
    val photoListState: State<PhotoListState> = _photoListState

    fun load() {
        viewModelScope.launch(AppDispatchers.io) {
            _photoListState.value = PhotoListState.Loading
            when (val result = photos.invoke(Unit)) {
                is Outcome.Failure<*> -> {
                    _photoListState.value = PhotoListState.LoadingFailed
                }
                is Outcome.Success<List<Photo>> -> {
                    _photoListState.value = PhotoListState.Content(photos = result.data)
                }
            }
        }
    }

    fun signout() {
        viewModelScope.launch {
            _signout()
        }
    }
}