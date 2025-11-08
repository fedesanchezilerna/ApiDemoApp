package org.ilerna.apidemoapp.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface DragonBallApiInterface {

    companion object {
        const val BASE_URL = "https://dragonball-api.com/api/"
        fun create(): DragonBallApiInterface {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
                GsonConverterFactory.create()
            ).client(client).build()
            return retrofit.create(DragonBallApiInterface::class.java)
        }
    }
}