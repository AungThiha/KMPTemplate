package aung.thiha.photo.album.photos.presentation.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aung.thiha.coroutines.AppDispatchers
import aung.thiha.operation.Outcome
import aung.thiha.operation.SuspendOperation
import aung.thiha.photo.album.photos.domain.model.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PhotoListViewModel(
    private val _signout: SuspendOperation<Unit, Unit>,
    private val photos: SuspendOperation<Unit, List<Photo>>
) : ViewModel(), PhotoListScreenListener {

    private val _photoListState = MutableStateFlow<PhotoListState>(PhotoListState.Loading)
    val photoListState: StateFlow<PhotoListState> = _photoListState

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

    override fun onRetryClicked() {
        load()
    }

    override fun onSignoutClicked() {
        viewModelScope.launch {
            _signout(Unit)
        }
    }
}