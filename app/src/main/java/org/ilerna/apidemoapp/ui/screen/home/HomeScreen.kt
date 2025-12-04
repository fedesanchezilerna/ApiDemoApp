package org.ilerna.apidemoapp.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.ilerna.apidemoapp.R
import org.ilerna.apidemoapp.domain.model.DBCharacter
import org.ilerna.apidemoapp.ui.components.InfoCard
import org.ilerna.apidemoapp.ui.components.InfoRow

/**
 * HomeScreen - Main screen that shows the DB Character list
 *
 * Components:
 * - Fixed header with DB logo
 * - Scrollable character list
 *
 * @param viewModel ViewModel for managing character data
 * @param onCharacterClick Callback when a character is clicked
 * @param modifier Optional modifier
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onCharacterClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val charactersResponse by viewModel.characters.observeAsState()
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

        // Scrollable character list
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            charactersResponse?.items?.let { charactersList ->
                items(charactersList) { character ->
                    CharacterCard(
                        character = character,
                        onClick = { onCharacterClick(character.id) }
                    )
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

/**
 * CharacterCard - Individual card for each character in the list
 *
 * @param character Dragon Ball character to display
 * @param onClick Callback when card is clicked
 * @param modifier Optional modifier
 */
@Composable
fun CharacterCard(
    character: DBCharacter,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    InfoCard(
        title = character.name,
        modifier = modifier.clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Character Image
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier
                    .size(90.dp)
            )

            // Character Information
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                InfoRow(label = "Race", value = character.race)
                InfoRow(label = "Gender", value = character.gender)
                InfoRow(label = "Ki", value = character.ki)
            }
        }
    }
}