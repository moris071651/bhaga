package com.tumba.bhaga.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun PercentChangeBadge(percentChange: Double, isPositiveChange: Boolean, modifier: Modifier = Modifier) {
    Surface(
        color = when(isPositiveChange) {
            true -> {
                Color.Green.copy(alpha = 0.1f)
            }
            false -> {
                Color.Red.copy(alpha = 0.1f)
            }
        },
        shape = RoundedCornerShape(50),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Icon(
                imageVector = when(isPositiveChange) {
                    true -> {
                        Icons.Filled.KeyboardArrowUp
                    }
                    false -> {
                        Icons.Filled.KeyboardArrowDown
                    }
                },
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = "${"%.2f".format(when(isPositiveChange) {
                    true -> {
                        percentChange
                    }
                    false -> {
                        - percentChange
                    }
                })}%",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium
            )
        }
    }
}