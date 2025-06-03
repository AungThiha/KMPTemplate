package aung.thiha.compose

import androidx.compose.material3.TopAppBar
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.painterResource
import photoalbum.compose.generated.resources.Res
import photoalbum.compose.generated.resources.ic_action_arrow_back_ios

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumTopAppBar(
    actions: @Composable RowScope.() -> Unit = {},
    onUpButtonClick: () -> Unit,
) {
    TopAppBar(
        title = { },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
        navigationIcon = {
            IconButton(onClick = onUpButtonClick) {
                Icon(
                    // TODO update to material 3 back button and delete ic_action_arrow_back_ios
                    painter = painterResource(Res.drawable.ic_action_arrow_back_ios),
                    contentDescription = "Up Button"
                )
            }
        },
        actions = actions
    )
}
