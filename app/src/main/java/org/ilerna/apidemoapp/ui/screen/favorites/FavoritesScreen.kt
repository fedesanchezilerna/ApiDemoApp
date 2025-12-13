package org.ilerna.apidemoapp.ui.screen.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import org.ilerna.apidemoapp.R
import org.ilerna.apidemoapp.ui.components.CharacterGridCard
import org.ilerna.apidemoapp.ui.components.CharacterListCard
import org.ilerna.apidemoapp.ui.components.DragonBallHeader
import org.ilerna.apidemoapp.ui.screen.settings.CharacterViewMode
import org.ilerna.apidemoapp.ui.screen.settings.SettingsViewModel
import org.ilerna.apidemoapp.ui.theme.AppTypography

/**
 * FavoritesScreen - Screen to display favorite characters
 *
 * Components:
 * - Fixed header with favorites title
 * - Scrollable favorite characters list (list or grid view)
 *
 * @param viewModel ViewModel for managing favorites data
 * @param settingsViewModel ViewModel for accessing settings like view mode
 * @param onCharacterClick Callback when a character is clicked
 * @param modifier Optional modifier
 */
@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    settingsViewModel: SettingsViewModel = viewModel(),
    onCharacterClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val favorites by viewModel.favorites.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val currentViewMode by settingsViewModel.currentCharactersViewMode.collectAsState()
    val listState = rememberLazyListState()
    val lifecycleOwner = LocalLifecycleOwner.current

    // Reload favorites when screen becomes visible (e.g., returning from details)
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadFavorites()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        // Fixed header
        DragonBallHeader()

        // Content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                favorites.isEmpty() -> {
                    Text(
                        text = "No favorites yet",
                        style = AppTypography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }

                else -> {
                    // Scrollable favorites list or grid based on view mode
                    when (currentViewMode) {
                        CharacterViewMode.LIST -> {
                            LazyColumn(
                                state = listState,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(favorites) { character ->
                                    CharacterListCard(
                                        character = character,
                                        onClick = { onCharacterClick(character.id) }
                                    )
                                }
                            }
                        }
                        CharacterViewMode.GRID -> {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 12.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(favorites) { character ->
                                    CharacterGridCard(
                                        character = character,
                                        onClick = { onCharacterClick(character.id) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
