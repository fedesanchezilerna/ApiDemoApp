package org.ilerna.apidemoapp.ui.screen.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
    onCharactersViewModeChanged: (CharacterViewMode) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val currentTheme by viewModel.currentTheme.collectAsState()
    val currentCharactersViewMode by viewModel.currentCharactersViewMode.collectAsState()
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
            options = ThemeMode.entries,
            onOptionSelected = { theme ->
                viewModel.setTheme(theme)
                onThemeChanged(theme)
            },
            contentDescription = "Select theme"
        )

        // View Setting
        SettingItemWithDropdown(
            title = "View mode",
            description = "Select the characters view mode",
            selectedOption = currentCharactersViewMode,
            options = CharacterViewMode.entries,
            onOptionSelected = { viewMode ->
                viewModel.setCharactersView(viewMode)
                onCharactersViewModeChanged(viewMode)
            },
            contentDescription = "Select view mode"
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
enum class ThemeMode : DisplayableOption {
    LIGHT,
    DARK,
    SYSTEM;

    override fun getDisplayName(): String = when (this) {
        LIGHT -> "Light"
        DARK -> "Dark"
        SYSTEM -> "System Default"
    }
}

/**
 * CharacterViewMode - Enum for character view mode selection
 */
enum class CharacterViewMode : DisplayableOption {
    LIST,
    GRID;

    override fun getDisplayName(): String = when (this) {
        LIST -> "List"
        GRID -> "Grid"
    }
}

/**
 * Interface for types that can be displayed in a dropdown
 */
interface DisplayableOption {
    fun getDisplayName(): String
}

/**
 * SettingItemWithDropdown - Generic reusable component for a setting with dropdown selector
 * 
 * @param T The type of the options (must implement DisplayableOption)
 * @param title The title of the setting
 * @param description The description of the setting
 * @param selectedOption The currently selected option
 * @param options List of all available options
 * @param onOptionSelected Callback when an option is selected
 * @param contentDescription Content description for accessibility
 * @param modifier Optional modifier
 */
@Composable
fun <T : DisplayableOption> SettingItemWithDropdown(
    title: String,
    description: String,
    selectedOption: T,
    options: List<T>,
    onOptionSelected: (T) -> Unit,
    contentDescription: String = "Select option",
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
                contentDescription = contentDescription,
                tint = colors.primary
            )
        }

        // Dropdown Menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option.getDisplayName(),
                            style = AppTypography.bodyMedium
                        )
                    },
                    onClick = {
                        onOptionSelected(option)
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