package com.tumba.bhaga.ui.screens.settings

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.tumba.bhaga.ui.screens.stockdetail.StockDetailViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = remember { SettingsViewModel() }
) {
    val isTokenValid by viewModel.isTokenValid.collectAsState()
    var tokenValidityEnabled by remember { mutableStateOf(true) }
    val context = LocalContext.current

    val tokenInitial by viewModel.tokenInitial.collectAsState()
    var token by remember { mutableStateOf("") }

    LaunchedEffect(tokenInitial) {
        token = tokenInitial
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("Cache Management", style = MaterialTheme.typography.titleMedium)

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                CacheRow("Stock data") { viewModel.clearStockCache() }
                CacheRow("Company data") { viewModel.clearCompanyCache() }
                CacheRow("News data") { viewModel.clearNewsCache() }
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = DividerDefaults.Thickness,
            color = DividerDefaults.color
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("API Token", style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = token,
                onValueChange = { token = it },
                label = { Text("Enter API token") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Button(
                enabled = tokenValidityEnabled,
                onClick = {
                    tokenValidityEnabled = false

                    viewModel.checkTokenValidity(token)
                    if (isTokenValid == true) {
                        viewModel.setNewToken(token)
                    }
                    else {
                        Toast.makeText(
                            context,
                            "Invalid token",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    tokenValidityEnabled = true
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Save")
            }
        }
    }
}

@Composable
private fun CacheRow(label: String, onClear: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label)
        IconButton(onClick = onClear) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Clear $label"
            )
        }
    }
}
