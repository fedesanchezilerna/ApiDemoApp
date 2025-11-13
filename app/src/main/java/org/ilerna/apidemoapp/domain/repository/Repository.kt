package org.ilerna.apidemoapp.domain.repository

import org.ilerna.apidemoapp.data.remote.DragonBallApiInterface

class Repository {
    private val apiInterface = DragonBallApiInterface.Companion.create()

    suspend fun getAllCharacters(page: Int = 1, limit: Int = 10) =
        apiInterface.getCharacters(page, limit)

    suspend fun getCharacterById(id: Int) =
        apiInterface.getCharacterById(id)

    suspend fun filterCharacters(
        name: String? = null,
        gender: String? = null,
        race: String? = null,
        affiliation: String? = null
    ) = apiInterface.filterCharacters(name, gender, race, affiliation)
}