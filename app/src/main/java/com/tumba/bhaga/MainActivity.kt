package com.tumba.bhaga

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.tumba.bhaga.domain.models.StockSummary
import com.tumba.bhaga.ui.components.BottomAppBar
import com.tumba.bhaga.ui.components.BottomBarOption
import com.tumba.bhaga.ui.components.StockSummaryList
import com.tumba.bhaga.ui.components.TopAppBar
import com.tumba.bhaga.ui.components.TopBarAction
import com.tumba.bhaga.ui.navigation.BhagaNavHost
import com.tumba.bhaga.ui.theme.BhagaTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            BhagaTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            screenTitle = "Home",
                            navigationAction = TopBarAction(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            ),
                            searchAction = TopBarAction(
                                imageVector = Icons.Filled.Search,
                            ),
                        )
                    },
                    content = { innerPadding ->
                        BhagaNavHost(
                            navController = navController,
                            modifier = Modifier.padding(innerPadding)
                        )
                    },
                    bottomBar = {
                        BottomAppBar(
                            selected = 2,
                            navOptions = listOf(
                                BottomBarOption(
                                    label = "Favorites",
                                    icon = Icons.Filled.Favorite
                                ),
                                BottomBarOption(
                                    label = "Home",
                                    icon = Icons.Filled.Home
                                ),
                                BottomBarOption(
                                    label = "Settings",
                                    icon = Icons.Filled.Settings
                                )
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun BhagaApp() {

}
