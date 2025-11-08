package org.ilerna.apidemoapp.api

import okhttp3.OkHttpClient
import org.ilerna.apidemoapp.model.Character
import org.ilerna.apidemoapp.model.PeopleData
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterface {
    @GET("people/")
    suspend fun getData(): Response<PeopleData>

    @POST("people")
    suspend fun addCharacter(@Body characterData: Character): Response<PeopleData>

    @PUT("people/{id}")
    suspend fun updateCharacter(@Path("id") characterId: Int, @Body character: Character): Response<PeopleData>

    @DELETE("people/{id}")
    suspend fun deleteCharacter(@Path("id") characterId: Int): Response<PeopleData>

    companion object {
        const val BASE_URL = "https://swapi.dev/api/"
        fun create(): ApiInterface {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
                GsonConverterFactory.create()
            ).client(client).build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}