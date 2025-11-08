package org.ilerna.apidemoapp.model

/**
 * Transformation model
 */
data class Transformation(
    val id: Int,
    val name: String,
    val image: String,
    val ki: String,
    val deletedAt: String? = null
)
