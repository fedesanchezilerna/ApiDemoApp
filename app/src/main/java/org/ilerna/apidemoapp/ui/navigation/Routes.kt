package org.ilerna.apidemoapp.ui.navigation

import kotlinx.serialization.Serializable

/**
 * Destinations - Sealed class that defines all navigation routes in the app
 */
sealed class Destinations {
    @Serializable
    object Home : Destinations()

    @Serializable
    object Favorites : Destinations()

    @Serializable
    object Settings : Destinations()

    @Serializable
    data class Details(val characterId: Int) : Destinations()
}
