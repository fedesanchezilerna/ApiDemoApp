package org.ilerna.apidemoapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.ilerna.apidemoapp.domain.model.Planet
import org.ilerna.apidemoapp.domain.model.Transformation

/**
 * FavoriteCharacterEntity - Room entity for storing favorite characters
 */
@Entity(tableName = "favorite_characters")
data class FavoriteCharacterEntity(
    @PrimaryKey
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
    val originPlanet: Planet? = null,
    val transformations: List<Transformation> = emptyList()
)
