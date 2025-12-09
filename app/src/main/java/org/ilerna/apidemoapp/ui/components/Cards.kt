package org.ilerna.apidemoapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.ilerna.apidemoapp.domain.model.DBCharacter
import org.ilerna.apidemoapp.ui.theme.AppTypography

/**
 * InfoCard - Reusable card container for information sections
 *
 * Used across multiple screens for displaying structured information.
 *
 * @param title The title displayed at the top of the card
 * @param modifier Optional modifier for customizing the card
 * @param content The composable content to display inside the card
 */
@Composable
fun InfoCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Card(
        border = BorderStroke(2.dp, colors.primary),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = AppTypography.titleMedium,
                color = colors.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp,
                color = colors.primary.copy(alpha = 0.3f)
            )
            content()
        }
    }
}

/**
 * InfoRow - Display a label-value pair in a consistent format
 *
 * Commonly used inside InfoCard to display structured key-value data.
 *
 * @param label The label text (e.g., "Race", "Gender")
 * @param value The value text to display
 * @param modifier Optional modifier
 */
@Composable
fun InfoRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            style = AppTypography.bodyMedium,
            color = colors.onSurface.copy(alpha = 0.7f),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            style = AppTypography.bodyMedium,
            color = colors.onSurface
        )
    }
}

/**
 * CharacterListCard - Card for displaying character in list view
 *
 * Displays character horizontally with image on left and info on right.
 *
 * @param character Dragon Ball character to display
 * @param onClick Callback when card is clicked
 * @param modifier Optional modifier
 */
@Composable
fun CharacterListCard(
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
                modifier = Modifier.size(90.dp)
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

/**
 * CharacterGridCard - Card for displaying character in grid view
 *
 * Displays character vertically with name centered and other info aligned left.
 *
 * @param character Dragon Ball character to display
 * @param onClick Callback when card is clicked
 * @param modifier Optional modifier
 */
@Composable
fun CharacterGridCard(
    character: DBCharacter,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    Card(
        border = BorderStroke(2.dp, colors.primary),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Character Name (Centered)
            Text(
                text = character.name,
                style = AppTypography.titleSmall,
                color = colors.primary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            // Character Image
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier.size(150.dp)
            )

            // Horizontal Divider
            HorizontalDivider(
                thickness = 1.dp,
                color = colors.primary.copy(alpha = 0.3f),
                modifier = Modifier.fillMaxWidth()
            )

            // Character Information (Left aligned)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                InfoRow(label = "Race", value = character.race)
                InfoRow(label = "Gender", value = character.gender)
                InfoRow(label = "Ki", value = character.ki)
            }
        }
    }
}
