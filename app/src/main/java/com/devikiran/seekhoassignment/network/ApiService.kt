package com.devikiran.seekhoassignment.network

import com.devikiran.seekhoassignment.data.AnimeDetailsResponse
import com.devikiran.seekhoassignment.data.TopAnimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    companion object {
        const val BASE_URL = "https://api.jikan.moe/v4/"
    }

    // Get top anime
    @GET("top/anime")
    fun getTopAnime(): Call<TopAnimeResponse>

    // Get details of a specific anime by ID
    @GET("anime/{anime_id}")
    fun getAnimeDetails(@Path("anime_id") animeId: Int): Call<AnimeDetailsResponse>
}