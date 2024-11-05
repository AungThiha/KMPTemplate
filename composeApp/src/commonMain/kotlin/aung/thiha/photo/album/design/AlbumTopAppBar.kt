package aung.thiha.photo.album.design

import androidx.compose.material.TopAppBar
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import photoalbum.composeapp.generated.resources.Res
import photoalbum.composeapp.generated.resources.ic_action_arrow_back_ios

@Composable
fun AlbumTopAppBar(
    actions: @Composable RowScope.() -> Unit = {},
    onUpButtonClick: () -> Unit,
) {
    TopAppBar(
        title = { },
        backgroundColor = Color.White,
        navigationIcon = {
            IconButton(onClick = onUpButtonClick) {
                Icon(
                    painter = painterResource(Res.drawable.ic_action_arrow_back_ios),
                    contentDescription = "Up Button"
                )
            }
        },
        elevation = 0.dp,
        actions = actions
    )
}
