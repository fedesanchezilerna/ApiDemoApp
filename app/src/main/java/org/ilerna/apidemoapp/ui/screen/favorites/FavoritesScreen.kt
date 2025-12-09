package org.ilerna.apidemoapp.ui.screen.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import org.ilerna.apidemoapp.R
import org.ilerna.apidemoapp.ui.screen.home.CharacterCard
import org.ilerna.apidemoapp.ui.theme.AppTypography

/**
 * FavoritesScreen - Screen to display favorite characters
 *
 * Components:
 * - Fixed header with favorites title
 * - Scrollable favorite characters list
 *
 * @param viewModel ViewModel for managing favorites data
 * @param onCharacterClick Callback when a character is clicked
 * @param modifier Optional modifier
 */
@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    onCharacterClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val favorites by viewModel.favorites.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
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
        FavoritesHeader()

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
                    // Scrollable favorites list
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(favorites) { character ->
                            CharacterCard(
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

/**
 * FavoritesHeader - Fixed header with favorites title
 *
 * This header remains visible even when scrolling the list
 */
@Composable
fun FavoritesHeader(modifier: Modifier = Modifier) {
    val colors = MaterialTheme.colorScheme
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Dragon Ball logo
        Image(
            painter = painterResource(id = R.drawable.dragon_ball_header),
            contentDescription = "Dragon Ball logo",
            modifier = Modifier
                .height(60.dp)
                .padding(horizontal = 8.dp)
        )
    }

    HorizontalDivider(
        thickness = 4.dp,
        color = colors.primary
    )
}
