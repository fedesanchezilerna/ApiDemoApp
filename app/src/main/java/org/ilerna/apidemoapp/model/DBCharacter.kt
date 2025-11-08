package org.ilerna.apidemoapp.model

/**
 * Character model
 */
data class DBCharacter(
    val id: Int,
    val name: String,
    val ki: String,
    val maxKi: String,
    val race: String,
    val gender: String,
    val description: String,
    val image: String,
    val affiliation: String,
    val deletedAt: String? = null,
    val originPlanet: Planet? = null,  // Only present when fetching by ID
    val transformations: List<Transformation> = emptyList()  // Only present when fetching by ID
)