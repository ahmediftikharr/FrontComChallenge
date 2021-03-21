package com.iftekhar.frontkom.androidchallenge.model

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import kotlin.coroutines.Continuation

object Webservice {
    private val BASE_URL = "https://newsapi.org/v2/"
    var newsApi: NewsApi

    init {
        newsApi =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder().build()
                ).build()
                .create(NewsApi::class.java)
    }
}

interface NewsApi {
    @GET("top-headlines")
    suspend fun getHeadlines(
        @Query("language") str: String?,
        @Query("apiKey") str2: String?,
        @Query("pageSize") i: Int
    ): HeadlineResponse
}
