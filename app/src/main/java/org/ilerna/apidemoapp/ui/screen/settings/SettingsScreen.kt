package org.ilerna.apidemoapp.ui.screen.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
 * @param onThemeChanged Callback when theme is changed
 * @param modifier Optional modifier
 */
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onThemeChanged: (ThemeMode) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val currentTheme by viewModel.currentTheme.collectAsState()
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

        // Theme Setting
        SettingItemWithDropdown(
            title = "Theme",
            description = "Select the theme for the app",
            selectedOption = currentTheme,
            onOptionSelected = { theme ->
                viewModel.setTheme(theme)
                onThemeChanged(theme)
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
 * ThemeMode - Enum for theme selection
 */
enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM;

    fun getDisplayName(): String = when (this) {
        LIGHT -> "Light"
        DARK -> "Dark"
        SYSTEM -> "System Default"
    }
}

/**
 * SettingItemWithDropdown - Reusable component for a setting with dropdown selector
 */
@Composable
fun SettingItemWithDropdown(
    title: String,
    description: String,
    selectedOption: ThemeMode,
    onOptionSelected: (ThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    var expanded by remember { mutableStateOf(false) }

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

        // Dropdown Button
        TextButton(
            onClick = { expanded = true }
        ) {
            Text(
                text = selectedOption.getDisplayName(),
                style = AppTypography.bodyMedium,
                color = colors.primary
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Select theme",
                tint = colors.primary
            )
        }

        // Dropdown Menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            ThemeMode.entries.forEach { theme ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = theme.getDisplayName(),
                            style = AppTypography.bodyMedium
                        )
                    },
                    onClick = {
                        onOptionSelected(theme)
                        expanded = false
                    }
                )
            }
        }
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