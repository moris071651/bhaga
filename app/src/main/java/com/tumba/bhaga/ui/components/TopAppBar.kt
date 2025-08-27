package com.tumba.bhaga.ui.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight

data class TopBarAction(
    val imageVector: ImageVector,
    val contentDescription: String? = null,
    val onClick: () -> Unit = { },
)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopAppBar(
    screenTitle: String,
    navigationAction: TopBarAction?,
    searchAction: TopBarAction?,
    scrollBehaviorTop: TopAppBarScrollBehavior? = null
) {
    CenterAlignedTopAppBar(
        navigationIcon = {
            if (navigationAction != null) {
                IconButton(onClick = navigationAction.onClick) {
                    Icon(
                        imageVector = navigationAction.imageVector,
                        contentDescription = navigationAction.contentDescription
                    )
                }
            }
        },

        actions = {
            if (searchAction != null) {
                IconButton(onClick = searchAction.onClick) {
                    Icon(
                        imageVector = searchAction.imageVector,
                        contentDescription = searchAction.contentDescription
                    )
                }
            }
        },

        title = {
            Text(
                text = screenTitle,
                fontWeight = FontWeight.Bold
            )
        },

        scrollBehavior = scrollBehaviorTop,
    )
}
