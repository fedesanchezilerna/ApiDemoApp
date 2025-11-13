package org.ilerna.apidemoapp.domain.model

/**
 * Planet model
 */
data class Planet(
    val id: Int,
    val name: String,
    val isDestroyed: Boolean,
    val description: String,
    val image: String,
    val deletedAt: String? = null
)
