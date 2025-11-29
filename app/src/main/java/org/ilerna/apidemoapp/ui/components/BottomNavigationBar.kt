package org.ilerna.apidemoapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.ilerna.apidemoapp.navigation.Destinations
import org.ilerna.apidemoapp.navigation.NavigationItem

/**
 * Displays a navigation bar with three items (Home, Favorites, Settings)
 * and a top separator line matching the app's header style.
 *
 * @param selectedItem Currently selected navigation item index
 * @param onItemSelected Callback when a navigation item is selected
 * @param navController Navigation controller for navigation actions
 * @param modifier Optional modifier
 */
@Composable
fun BottomNavigationBar(
    selectedItem: Int,
    onItemSelected: (Int, Destinations) -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    
    val items = listOf(
        NavigationItem("Home", Icons.Default.Home, Destinations.Home, 0),
        NavigationItem("Favorites", Icons.Default.Star, Destinations.Favorites, 1),
        NavigationItem("Settings", Icons.Default.Settings, Destinations.Settings, 2)
    )

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // Separator line like in the header
        HorizontalDivider(
            thickness = 4.dp,
            color = colors.primary
        )
        
        NavigationBar(
            containerColor = colors.background
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = item.index == selectedItem,
                    label = { Text(item.label) },
                    icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                    onClick = {
                        onItemSelected(index, item.route)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = colors.primary,
                        selectedTextColor = colors.primary,
                        indicatorColor = colors.surfaceVariant,
                        unselectedIconColor = colors.outline,
                        unselectedTextColor = colors.outline
                    )
                )
            }
        }
    }
}
