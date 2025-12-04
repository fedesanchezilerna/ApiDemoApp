package org.ilerna.apidemoapp.ui.screen.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.ilerna.apidemoapp.ui.components.ConfirmationDialog
import org.ilerna.apidemoapp.ui.theme.AppTypography

/**
 * SettingsScreen - Screen to manage app settings
 * 
 * @param viewModel ViewModel for managing settings state
 * @param onDarkModeChanged Callback when dark mode is toggled
 * @param modifier Optional modifier
 */
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onDarkModeChanged: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val colors = MaterialTheme.colorScheme
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Text(
            text = "Settings",
            style = AppTypography.headlineMedium,
            color = colors.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Dark Mode Setting
        SettingItem(
            title = "Dark Mode",
            description = "Enable dark theme for the app",
            isEnabled = isDarkMode,
            onToggle = { enabled ->
                viewModel.setDarkMode(enabled)
                onDarkModeChanged(enabled)
            }
        )

        // Delete All Favorites Setting
        SettingItemWithButton(
            title = "Delete all favorites",
            description = "Remove all favorite characters added",
            buttonText = "Delete",
            onButtonClick = { showDeleteConfirmDialog = true }
        )
    }
    
    // Delete Confirmation Dialog
    if (showDeleteConfirmDialog) {
        ConfirmationDialog(
            title = "Delete all favorites?",
            message = "This action will remove all favorite characters. This cannot be undone.",
            confirmButtonText = "Delete",
            dismissButtonText = "Cancel",
            onConfirm = {
                viewModel.deleteAllFavorites()
                showDeleteConfirmDialog = false
            },
            onDismiss = {
                showDeleteConfirmDialog = false
            }
        )
    }
}

/**
 * SettingItem - Reusable component for a setting with toggle switch
 */
@Composable
fun SettingItem(
    title: String,
    description: String,
    isEnabled: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = AppTypography.titleMedium,
                color = colors.onSurface,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = description,
                style = AppTypography.bodyMedium,
                color = colors.onSurface.copy(alpha = 0.7f)
            )
        }

        Switch(
            checked = isEnabled,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedThumbColor = colors.primary,
                checkedTrackColor = colors.primaryContainer,
                uncheckedThumbColor = colors.outline,
                uncheckedTrackColor = colors.surfaceVariant
            )
        )
    }
}

/**
 * SettingItemWithButton - Reusable component for a setting with action button
 */
@Composable
fun SettingItemWithButton(
    title: String,
    description: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val buttonColors = ButtonDefaults.buttonColors(
        containerColor = colors.errorContainer,
        contentColor = colors.onErrorContainer
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = AppTypography.titleMedium,
                color = colors.onSurface,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = description,
                style = AppTypography.bodyMedium,
                color = colors.onSurface.copy(alpha = 0.7f)
            )
        }

        Button(
            onClick = onButtonClick,
            colors = buttonColors
        ) {
            Text(
                text = buttonText,
                style = AppTypography.bodyMedium
            )
        }
    }
}