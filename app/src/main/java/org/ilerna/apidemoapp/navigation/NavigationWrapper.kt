package org.ilerna.apidemoapp.navigation

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
import org.ilerna.apidemoapp.ui.screen.home.HomeScreen
import org.ilerna.apidemoapp.ui.screen.home.HomeViewModel
import org.ilerna.apidemoapp.ui.screen.settings.SettingsScreen

/**
 * NavigationWrapper - Centralized navigation configuration using NavHost
 *
 * @param navController Navigation controller passed from parent composable
 * @param modifier Optional modifier
 */
@Composable
fun NavigationWrapper(
    navController: NavHostController,
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
            FavoritesScreen()
        }

        composable<Destinations.Settings> {
            SettingsScreen()
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
