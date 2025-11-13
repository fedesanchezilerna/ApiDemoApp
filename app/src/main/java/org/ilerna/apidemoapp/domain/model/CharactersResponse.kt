package org.ilerna.apidemoapp.domain.model

/**
 * Response from Dragon Ball API for paginated characters
 */
data class CharactersResponse(
    val items: List<DBCharacter>,
    val meta: Meta,
    val links: Links
)

/**
 * Metadata for pagination
 */
data class Meta(
    val totalItems: Int,
    val itemCount: Int,
    val itemsPerPage: Int,
    val totalPages: Int,
    val currentPage: Int
)

/**
 * Links for navigation between pages
 */
data class Links(
    val first: String,
    val previous: String?,
    val next: String?,
    val last: String
)
