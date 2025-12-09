package org.ilerna.apidemoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.ilerna.apidemoapp.ui.screen.details.DetailsScreen
import org.ilerna.apidemoapp.ui.screen.details.DetailsViewModel
import org.ilerna.apidemoapp.ui.screen.favorites.FavoritesScreen
import org.ilerna.apidemoapp.ui.screen.favorites.FavoritesViewModel
import org.ilerna.apidemoapp.ui.screen.home.HomeScreen
import org.ilerna.apidemoapp.ui.screen.home.HomeViewModel
import org.ilerna.apidemoapp.ui.screen.settings.SettingsScreen
import org.ilerna.apidemoapp.ui.screen.settings.SettingsViewModel
import org.ilerna.apidemoapp.ui.screen.settings.ThemeMode

/**
 * NavigationWrapper - Centralized navigation configuration using NavHost
 *
 * @param navController Navigation controller passed from parent composable
 * @param settingsViewModel Shared ViewModel for settings
 * @param onThemeChanged Callback when theme is changed
 * @param modifier Optional modifier
 */
@Composable
fun NavigationWrapper(
    navController: NavHostController,
    settingsViewModel: SettingsViewModel,
    onThemeChanged: (ThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.Home,
        modifier = modifier
    ) {
        composable<Destinations.Home> {
            val viewModel: HomeViewModel = viewModel()
            HomeScreen(
                viewModel = viewModel,
                onCharacterClick = { characterId ->
                    navController.navigate(Destinations.Details(characterId))
                }
            )
        }

        composable<Destinations.Favorites> {
            val viewModel: FavoritesViewModel = viewModel()
            FavoritesScreen(
                viewModel = viewModel,
                onCharacterClick = { characterId ->
                    navController.navigate(Destinations.Details(characterId))
                }
            )
        }

        composable<Destinations.Settings> {
            SettingsScreen(
                viewModel = settingsViewModel,
                onThemeChanged = onThemeChanged
            )
        }

        composable<Destinations.Details> { backStackEntry ->
            val details = backStackEntry.toRoute<Destinations.Details>()
            val viewModel: DetailsViewModel = viewModel()
            DetailsScreen(
                characterId = details.characterId,
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
