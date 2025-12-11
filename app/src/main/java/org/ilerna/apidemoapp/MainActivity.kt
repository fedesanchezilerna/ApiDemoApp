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
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import org.ilerna.apidemoapp.data.repository.SettingsRepository
import org.ilerna.apidemoapp.ui.navigation.NavigationWrapper
import org.ilerna.apidemoapp.ui.components.BottomNavigationBar
import org.ilerna.apidemoapp.ui.screen.settings.SettingsViewModel
import org.ilerna.apidemoapp.ui.screen.settings.SettingsViewModelFactory
import org.ilerna.apidemoapp.ui.screen.settings.ThemeMode
import org.ilerna.apidemoapp.ui.theme.AppTheme
import androidx.compose.foundation.isSystemInDarkTheme

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
    val settingsViewModel: SettingsViewModel = viewModel(
        factory = SettingsViewModelFactory(SettingsRepository(LocalContext.current))
    )
    val currentTheme by settingsViewModel.currentTheme.collectAsState()
    val isSystemInDarkTheme = isSystemInDarkTheme()

    // Determine dark theme based on current theme mode
    val isDarkTheme = when (currentTheme) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme
    }

    AppTheme(darkTheme = isDarkTheme) {
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
                onThemeChanged = { theme ->
                    settingsViewModel.setTheme(theme)
                },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}