package org.ilerna.apidemoapp.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.ilerna.apidemoapp.model.CharactersResponse
import org.ilerna.apidemoapp.model.DBCharacter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DragonBallApiInterface {

    // Obtener lista de personajes con paginación
    @GET("characters")
    suspend fun getCharacters(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): Response<CharactersResponse>

    // Obtener personaje por ID (incluye originPlanet y transformations)
    @GET("characters/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Response<DBCharacter>

    // Filtrar personajes (sin paginación)
    @GET("characters")
    suspend fun filterCharacters(
        @Query("name") name: String? = null,
        @Query("gender") gender: String? = null,
        @Query("race") race: String? = null,
        @Query("affiliation") affiliation: String? = null
    ): Response<List<DBCharacter>>

    companion object {
        const val BASE_URL = "https://dragonball-api.com/api/"

        fun create(): DragonBallApiInterface {
            // Logging interceptor para debug
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(DragonBallApiInterface::class.java)
        }
    }
}