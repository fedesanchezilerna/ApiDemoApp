package org.ilerna.apidemoapp.ui.screen.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
    }
}

/**
 * SettingItem - Reusable component for a setting with toggle
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
