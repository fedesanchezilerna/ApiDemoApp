package org.ilerna.apidemoapp.ui.screen.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import org.ilerna.apidemoapp.domain.model.DBCharacter
import org.ilerna.apidemoapp.domain.model.Planet
import org.ilerna.apidemoapp.domain.model.Transformation
import org.ilerna.apidemoapp.ui.components.ImageViewerDialog
import org.ilerna.apidemoapp.ui.components.InfoCard
import org.ilerna.apidemoapp.ui.components.InfoRow
import org.ilerna.apidemoapp.ui.theme.AppTypography

/**
 * DetailsScreen - Screen that displays detailed information about a character
 *
 * @param characterId ID of the character to display
 * @param viewModel ViewModel for fetching character details
 * @param onBackClick Callback when back button is clicked
 * @param modifier Optional modifier
 */
@Composable
fun DetailsScreen(
    characterId: Int,
    viewModel: DetailsViewModel,
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val character by viewModel.character.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)
    val error by viewModel.error.observeAsState()

    // Fetch character details when screen is displayed
    LaunchedEffect(characterId) {
        viewModel.getCharacterById(characterId)
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            }

            error != null -> {
                Text(
                    text = error ?: "Unknown error",
                    style = AppTypography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }

            character != null -> {
                CharacterDetailsContent(
                    character = character!!,
                    onBackClick = onBackClick
                )
            }
        }
    }
}

/**
 * CharacterDetailsContent - Displays the full character information
 */
@Composable
fun CharacterDetailsContent(
    character: DBCharacter,
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    var selectedTransformation by remember { mutableStateOf<Transformation?>(null) }
    var showCharacterImage by remember { mutableStateOf(false) }
    var showPlanetImage by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.background)
        ) {
        // Character Image Header
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(colors.primaryContainer)
                    .clickable { showCharacterImage = true },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = character.image,
                    contentDescription = character.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
        }

        // Character Name
        item {
            Text(
                text = character.name,
                style = AppTypography.headlineLarge,
                color = colors.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            )
        }

        // Basic Information Card
        item {
            InfoCard(title = "Basic Information") {
                InfoRow(label = "Race", value = character.race)
                InfoRow(label = "Gender", value = character.gender)
                InfoRow(label = "Affiliation", value = character.affiliation)
                InfoRow(label = "Ki", value = character.ki)
                InfoRow(label = "Max Ki", value = character.maxKi)
            }
        }

        // Description Card
        item {
            InfoCard(title = "Description") {
                Text(
                    text = character.description,
                    style = AppTypography.bodyMedium,
                    color = colors.onSurface,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // Origin Planet Card (if available)
        character.originPlanet?.let { planet ->
            item {
                PlanetCard(
                    planet = planet,
                    onImageClick = { showPlanetImage = true }
                )
            }
        }

        // Transformations Section (if available)
        if (character.transformations.isNotEmpty()) {
            item {
                Text(
                    text = "Transformations",
                    style = AppTypography.titleLarge,
                    color = colors.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                )
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(character.transformations) { transformation ->
                        TransformationCard(
                            transformation = transformation,
                            onClick = { selectedTransformation = transformation }
                        )
                    }
                }
            }
        }

            // Bottom spacer
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Back button in top-right corner
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = colors.primary,
                modifier = Modifier.size(32.dp)
            )
        }
    }

    // Image viewer dialogs
    if (showCharacterImage) {
        ImageViewerDialog(
            imageUrl = character.image,
            title = character.name,
            onDismiss = { showCharacterImage = false }
        )
    }

    selectedTransformation?.let { transformation ->
        ImageViewerDialog(
            imageUrl = transformation.image,
            title = transformation.name,
            onDismiss = { selectedTransformation = null }
        )
    }

    character.originPlanet?.let { planet ->
        if (showPlanetImage) {
            ImageViewerDialog(
                imageUrl = planet.image,
                title = planet.name,
                onDismiss = { showPlanetImage = false }
            )
        }
    }
}

/**
 * PlanetCard - Card displaying origin planet information
 */
@Composable
fun PlanetCard(
    planet: Planet,
    onImageClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    InfoCard(
        title = "Origin Planet",
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Planet Image
            AsyncImage(
                model = planet.image,
                contentDescription = planet.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .clickable { onImageClick() }
            )

            // Planet Info
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = planet.name,
                    style = AppTypography.titleSmall,
                    color = colors.onSurface,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (planet.isDestroyed) "Status: Destroyed" else "Status: Active",
                    style = AppTypography.bodySmall,
                    color = if (planet.isDestroyed) colors.error else colors.primary
                )
                Text(
                    text = planet.description,
                    style = AppTypography.bodySmall,
                    color = colors.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

/**
 * TransformationCard - Card displaying transformation information
 */
@Composable
fun TransformationCard(
    transformation: Transformation,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    Card(
        border = BorderStroke(1.dp, colors.primary),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
        ),
        modifier = modifier
            .width(150.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Transformation Image
            AsyncImage(
                model = transformation.image,
                contentDescription = transformation.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Transformation Name
            Text(
                text = transformation.name,
                style = AppTypography.bodySmall,
                color = colors.onSurface,
                fontWeight = FontWeight.Bold,
                maxLines = 2
            )

            // Transformation Ki
            Text(
                text = "Ki: ${transformation.ki}",
                style = AppTypography.bodySmall,
                color = colors.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}