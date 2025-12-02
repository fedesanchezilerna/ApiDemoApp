package org.ilerna.apidemoapp.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * NavigationItem - Data class that defines an item in the Bottom Navigation Bar
 *
 * @param label Text label to display for this navigation item
 * @param icon Icon to display for this navigation item
 * @param route Destination route to navigate to when clicked
 * @param index Position index in the bottom bar (0-based)
 */
data class NavigationItem(
    val label: String,
    val icon: ImageVector,
    val route: Destinations,
    val index: Int
)
