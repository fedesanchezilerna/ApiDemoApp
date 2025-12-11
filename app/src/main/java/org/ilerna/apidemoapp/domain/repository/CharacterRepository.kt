package org.ilerna.apidemoapp.domain.repository

import org.ilerna.apidemoapp.CharacterApplication
import org.ilerna.apidemoapp.data.local.entity.FavoriteCharacterEntity
import org.ilerna.apidemoapp.data.remote.DragonBallApiInterface
import org.ilerna.apidemoapp.domain.model.DBCharacter

class CharacterRepository {
    private val apiInterface = DragonBallApiInterface.Companion.create()
    private val daoInterface = CharacterApplication.database.characterDao()

    // API functions
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

    // Database functions
    suspend fun saveAsFavorite(character: DBCharacter) {
        val entity = FavoriteCharacterEntity(
            id = character.id,
            name = character.name,
            ki = character.ki,
            maxKi = character.maxKi,
            race = character.race,
            gender = character.gender,
            description = character.description,
            image = character.image,
            affiliation = character.affiliation,
            deletedAt = character.deletedAt,
            originPlanet = character.originPlanet,
            transformations = character.transformations
        )
        daoInterface.addFavorite(entity)
    }

    suspend fun deleteFavorite(characterId: Int) =
        daoInterface.deleteFavoriteById(characterId)

    suspend fun isFavorite(characterId: Int) =
        daoInterface.isFavorite(characterId)

    suspend fun getFavorites() =
        daoInterface.getAllFavorites()

    suspend fun deleteAllFavorites() =
        daoInterface.deleteAllFavorites()
}