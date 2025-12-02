package org.ilerna.apidemoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import org.ilerna.apidemoapp.ui.navigation.NavigationWrapper
import org.ilerna.apidemoapp.ui.components.BottomNavigationBar
import org.ilerna.apidemoapp.ui.screen.settings.SettingsViewModel
import org.ilerna.apidemoapp.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApp()
        }
    }
}

/**
 * MyApp - Main composable that sets up the app scaffold with navigation
 */
@Composable
fun MyApp() {
    var selectedItem: Int by remember { mutableIntStateOf(0) }
    val navController = rememberNavController()
    val settingsViewModel: SettingsViewModel = viewModel()
    val isDarkMode by settingsViewModel.isDarkMode.collectAsState()

    AppTheme(darkTheme = isDarkMode) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomNavigationBar(
                    selectedItem = selectedItem,
                    onItemSelected = { index, destination ->
                        selectedItem = index
                        navController.navigate(destination)
                    },
                    navController = navController
                )
            }
        ) { innerPadding ->
            NavigationWrapper(
                navController = navController,
                settingsViewModel = settingsViewModel,
                onDarkModeChanged = { enabled ->
                    settingsViewModel.setDarkMode(enabled)
                },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}