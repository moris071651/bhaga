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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
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

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            var selectedOption by remember { mutableIntStateOf(1) }
            val navOption = remember {
                mutableStateListOf(
                    BottomBarOption(
                        label = "Favourites",
                        icon = Icons.Filled.Favorite,
                        onClick = {
                            selectedOption = 0
                            navController.navigate("favourites")
                        }
                    ),
                    BottomBarOption(
                        label = "Home",
                        icon = Icons.Filled.Home,
                        onClick = {
                            selectedOption = 1
                            navController.navigate("home")
                        }

                    ),
                    BottomBarOption(
                        label = "Settings",
                        icon = Icons.Filled.Settings,
                        onClick = {
                            selectedOption = 2
                            navController.navigate("settings")
                        }
                    )
                )
            }

            val isHome = currentRoute != "home"
            val screenTitle = currentRoute
                ?.substringBefore("/")
                ?.replaceFirstChar { it.uppercaseChar() }
                ?: "Unknown"

            BhagaTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            screenTitle = screenTitle,
                            navigationAction = if (isHome) {
                                TopBarAction(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    onClick = { navController.popBackStack() }
                                )
                            } else null,
                            searchAction = TopBarAction(
                                imageVector = Icons.Filled.Search,
                                onClick = {  }
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
                            selected = selectedOption,
                            navOptions = navOption
                        )
                    }
                )
            }
        }
    }
}
