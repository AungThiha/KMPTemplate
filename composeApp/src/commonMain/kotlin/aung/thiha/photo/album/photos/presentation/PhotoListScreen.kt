package aung.thiha.photo.album.photos.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import aung.thiha.photo.album.koin.getViewModel
import aung.thiha.photo.album.photos.domain.model.Photo
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import photoalbum.composeapp.generated.resources.Res
import photoalbum.composeapp.generated.resources.ic_action_signout

@Composable
fun PhotoListScreen(
    navHostController: NavHostController
) {
    val viewModel = getViewModel<PhotoListViewModel>()
    val photoListState by remember { viewModel.photoListState }

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                backgroundColor = Color.White,
                elevation = 0.dp,
                actions = {
                    IconButton(onClick = {
                        viewModel.signout()
                    }) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_action_signout),
                            contentDescription = "Signout Button"
                        )
                    }
                }
            )
        }
    ) { contentPadding ->

        when (photoListState) {
            is PhotoListState.Content -> {
                if ((photoListState as PhotoListState.Content).photos.isEmpty()) {
                    EmptyPhotoGrid()
                } else {
                    PhotoGrid((photoListState as PhotoListState.Content).photos)
                }
            }
            PhotoListState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f)) // Semi-transparent background
                        .clickable(enabled = false) {} // Disables clicks on the overlay
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
            PhotoListState.LoadingFailed -> {
                PhotoLoadingFailed(onRetry = { viewModel.load() })
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
            items(photos.size) { index ->
                val photo = photos[index]
                Box(modifier = Modifier.padding(8.dp)) {
                    AsyncImage(
                        model = photo.url,
                        contentDescription = "photo",
                    )
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
            Text(text = "Retry", color = Color.White)
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