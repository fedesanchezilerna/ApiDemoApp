package org.ilerna.apidemoapp.ui.screen.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.ilerna.apidemoapp.ui.theme.AppTypography

/**
 * FavoritesScreen - Basic screen to display favorites
 */
@Composable
fun FavoritesScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Favorites Screen",
            style = AppTypography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
