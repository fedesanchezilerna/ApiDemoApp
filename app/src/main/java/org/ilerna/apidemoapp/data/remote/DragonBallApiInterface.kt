package org.ilerna.apidemoapp.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.ilerna.apidemoapp.domain.model.CharactersResponse
import org.ilerna.apidemoapp.domain.model.DBCharacter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DragonBallApiInterface {

    // Get Dragon Ball characters with pagination
    @GET("characters")
    suspend fun getCharacters(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): Response<CharactersResponse>

    // Get character by ID (includes originPlanet and transformations)
    @GET("characters/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Response<DBCharacter>

    // Filter characters (without pagination)
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
            // Logging interceptor for debug
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(DragonBallApiInterface::class.java)
        }
    }
}