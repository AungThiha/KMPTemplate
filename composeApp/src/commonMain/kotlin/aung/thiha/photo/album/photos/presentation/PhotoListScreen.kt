package aung.thiha.photo.album.photos.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import aung.thiha.photo.album.photos.data.remote.service.PhotosService
import aung.thiha.photo.album.restartApp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.compose.getKoin
import org.koin.core.context.stopKoin

@Composable
fun PhotoListScreen(
    navHostController: NavHostController
) {
    val photosService = getKoin().get<PhotosService>()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Photo List")
        Button(
            onClick = {
                GlobalScope.launch {
                    photosService.justChecking()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Just Check", color = Color.White)
        }
    }
}