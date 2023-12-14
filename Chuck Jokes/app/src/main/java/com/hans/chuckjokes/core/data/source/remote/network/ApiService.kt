package com.hans.chuckjokes.core.data.source.remote.network

import com.hans.chuckjokes.core.data.source.remote.response.JokesResponse
import com.hans.chuckjokes.core.data.source.remote.response.ListJokesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @Headers(
        "accept: application/json",
        "X-RapidAPI-Key: 858c9e173amsh94a0aabdb9b2904p1991e0jsn60300e3be7dc",
        "X-RapidAPI-Host: matchilling-chuck-norris-jokes-v1.p.rapidapi.com"
    )
    @GET("jokes/random")
    fun getRandomJokes(): Call<JokesResponse>

    @Headers(
        "accept: application/json",
        "X-RapidAPI-Key: 858c9e173amsh94a0aabdb9b2904p1991e0jsn60300e3be7dc",
        "X-RapidAPI-Host: matchilling-chuck-norris-jokes-v1.p.rapidapi.com"
    )
    @GET("jokes/search")
    fun getSearchJokes(@Query("query") query: String): Call<ListJokesResponse>
}