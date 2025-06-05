package aung.thiha.photo.album.photos.presentation.overview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import aung.thiha.photo.album.photos.domain.model.Photo
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.valentinilk.shimmer.shimmer
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PhotoListContainer() {
    val viewModel = koinViewModel<PhotoListViewModel>()
    val photoListState by viewModel.photoListState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.load()
    }
    PhotoListScreen(
        photoListState = photoListState,
        listener = viewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoListScreen(
    photoListState: PhotoListState,
    listener: PhotoListScreenListener,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Photos")
                },
//                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                actions = {
                    OutlinedButton(
                        shape = RoundedCornerShape(16.dp),
                        onClick = listener::onSignoutClick,
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Text("Sign out")
                    }
                }
            )
        }
    ) { contentPadding ->

        Box(modifier = Modifier.padding(contentPadding)) {
            when (photoListState) {
                is PhotoListState.Content -> {
                    if (photoListState.photos.isEmpty()) {
                        EmptyPhotoGrid()
                    } else {
                        PhotoGrid(photoListState.photos)
                    }
                }
                PhotoListState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .clickable(enabled = false) {} // Disables clicks on the overlay
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                }
                PhotoListState.LoadingFailed -> {
                    PhotoLoadingFailed(onRetry = listener::onSignoutClick)
                }
            }
        }
    }
}

@Composable
fun PhotoGrid(photos: List<Photo>) {
    LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(photos.size, key = { index -> photos[index].id }) { index ->
                val photo = photos[index]
                val painter = rememberAsyncImagePainter(model = photo.url)
                Box(modifier = Modifier.padding(8.dp)) {
                    Image(
                        painter = painter,
                        contentDescription = "photo",
                    )
                    if (painter.state.collectAsStateWithLifecycle().value is AsyncImagePainter.State.Loading) {
                        Box(modifier = Modifier
                            .aspectRatio(1f)
                            .shimmer()
                        ) {
                            Box(modifier = Modifier.fillMaxSize().background(Color.LightGray))
                        }
                    }

                }
            }
        }
}

@Composable
fun PhotoLoadingFailed(onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Loading Photos Failed!")

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onRetry,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Retry")
        }
    }
}

@Composable
fun EmptyPhotoGrid() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "No Photos")
    }
}
