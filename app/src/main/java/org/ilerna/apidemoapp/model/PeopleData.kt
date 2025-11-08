package org.ilerna.apidemoapp.model

data class PeopleData(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Character>
)
