package org.ilerna.apidemoapp.repository

import org.ilerna.apidemoapp.api.DragonBallApiInterface

class Repository {
    val dragonBallApiInterface = DragonBallApiInterface.create()
    suspend fun getAllCharacters() = dragonBallApiInterface.getData()
}