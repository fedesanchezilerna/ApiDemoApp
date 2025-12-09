package org.ilerna.apidemoapp.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.ilerna.apidemoapp.R
import org.ilerna.apidemoapp.ui.components.CharacterGridCard
import org.ilerna.apidemoapp.ui.components.CharacterListCard
import org.ilerna.apidemoapp.ui.screen.settings.CharacterViewMode
import org.ilerna.apidemoapp.ui.screen.settings.SettingsViewModel

/**
 * HomeScreen - Main screen that shows the DB Character list
 *
 * Components:
 * - Fixed header with DB logo
 * - Scrollable character list (list or grid view)
 *
 * @param viewModel ViewModel for managing character data
 * @param settingsViewModel ViewModel for accessing settings
 * @param onCharacterClick Callback when a character is clicked
 * @param modifier Optional modifier
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    settingsViewModel: SettingsViewModel = viewModel(),
    onCharacterClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val charactersResponse by viewModel.characters.observeAsState()
    val currentViewMode by settingsViewModel.currentCharactersViewMode.collectAsState()
    val listState = rememberLazyListState()

    // Load characters when the screen is displayed
    viewModel.getCharacters()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        // Fixed header with Dragon Ball logo
        DragonBallHeader()

        // Scrollable character list or grid based on view mode
        charactersResponse?.items?.let { charactersList ->
            when (currentViewMode) {
                CharacterViewMode.LIST -> {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    ) {
                        items(charactersList) { character ->
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
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    ) {
                        items(charactersList) { character ->
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

/**
 * DragonBallHeader -  Fixed header with Dragon Ball logo
 *
 * This header remains visible even when scrolling the list
 */
@Composable
fun DragonBallHeader(modifier: Modifier = Modifier) {
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