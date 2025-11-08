package org.ilerna.apidemoapp.repository

import org.ilerna.apidemoapp.api.ApiInterface

class Repository {
    val apiInterface = ApiInterface.create()
    suspend fun getAllCharacters() = apiInterface.getData()
}