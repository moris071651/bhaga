package com.tumba.bhaga.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight

data class BottomBarOption(
    val label: String,
    val icon: ImageVector,
    val onClick: () -> Unit = { },
    val contentDescription: String? = null,
)

@Composable
fun BottomAppBar(navOptions: List<BottomBarOption>, selected: Int) {
    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
        repeat(navOptions.size) { i ->
            NavigationBarItem(
                selected = selected == i,
                onClick = navOptions[i].onClick,
                icon = {
                    Icon(
                        navOptions[i].icon,
                        contentDescription = navOptions[i].contentDescription,
                    )
                },
                label = {
                    Text(
                        text = navOptions[i].label,
                        fontWeight = if (selected == i) {
                            FontWeight.Bold
                        }
                        else {
                            null
                        }
                    )
                }
            )
        }
    }
}
