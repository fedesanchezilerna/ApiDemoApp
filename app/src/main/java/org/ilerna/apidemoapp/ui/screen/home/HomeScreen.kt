package org.ilerna.apidemoapp.ui.screen.home

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import org.ilerna.apidemoapp.ui.theme.AppTypography

/**
 * HomeScreen - Pantalla principal que muestra la lista de personajes de Dragon Ball
 *
 * Componentes:
 * - Header fijo con logo de Dragon Ball
 * - Lista scrolleable de personajes
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

    // Load characters when show the screen
    viewModel.getCharacters()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        // Header fixed
        DragonBallHeader()

        // Scrollable character list
        LazyColumn(
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
 * DragonBallHeader - Fixed header with the Dragon Ball logo
 *
 * This header remains visible even when scrolling through the list
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
            contentDescription = "Dragon Ball Logo",
            modifier = Modifier
                .height(60.dp)
                .padding(horizontal = 16.dp)
        )
    }

    // Orange divider (secondary Dragon Ball color)
    HorizontalDivider(
        thickness = 10.dp,
        color = colors.primaryContainer
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
    val colors = MaterialTheme.colorScheme

    Card(
        border = BorderStroke(2.dp, colors.primary),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.primaryContainer
        ),
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Character Image
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Character Information
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Character Name
                Text(
                    text = character.name,
                    style = AppTypography.titleLarge,
                    color = colors.onPrimaryContainer,
                    modifier = Modifier.fillMaxWidth()
                )

                // Character Race
                Text(
                    text = "Race: ${character.race}",
                    style = AppTypography.bodyMedium,
                    color = colors.onPrimaryContainer.copy(alpha = 0.8f),
                    modifier = Modifier.fillMaxWidth()
                )

                // Power Level (Ki)
                Text(
                    text = "Ki: ${character.ki}",
                    style = AppTypography.bodySmall,
                    color = colors.onPrimaryContainer,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}