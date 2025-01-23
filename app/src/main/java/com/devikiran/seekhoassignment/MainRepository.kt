package com.devikiran.seekhoassignment

import android.util.Log
import com.devikiran.seekhoassignment.data.AnimeDetailsResponse
import com.devikiran.seekhoassignment.data.TopAnimeResponse
import com.devikiran.seekhoassignment.network.ApiService
import com.devikiran.seekhoassignment.util.ApiState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository {

    fun fetchTopAnime(apiService: ApiService, onResponse: (ApiState) -> Unit) {

        onResponse(ApiState.Loading)

        apiService.getTopAnime().enqueue(object : Callback<TopAnimeResponse> {
            override fun onResponse(
                call: Call<TopAnimeResponse>,
                response: Response<TopAnimeResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.data?.let { animeList ->
                        onResponse(ApiState.TopAnimeSuccess(animeList))
                    }
                } else {
                    onResponse(ApiState.Failure("API Error - Error Code: ${response.code()}"))
                    Log.e("API Error", "Error Code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TopAnimeResponse>, t: Throwable) {
                onResponse(ApiState.Failure("Network Error - ${t.message.toString()}"))
                Log.e("Network Error", t.message.toString())
            }
        })
    }

    fun fetchAnimeDetails(apiService: ApiService, animeId: Int, onResponse: (ApiState) -> Unit) {
        onResponse(ApiState.Loading)
        apiService.getAnimeDetails(animeId).enqueue(object : Callback<AnimeDetailsResponse> {
            override fun onResponse(
                call: Call<AnimeDetailsResponse>,
                response: Response<AnimeDetailsResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.data?.let { details ->
                        onResponse(ApiState.DetailedAnimeSuccess(details))
                        Log.d(
                            "AnimeDetails",
                            "Title: ${details.title}, Synopsis: ${details.synopsis}"
                        )
                    }
                } else {
                    onResponse(ApiState.Failure("API Error - Error Code: ${response.code()}"))
                    Log.e("API Error", "Error Code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<AnimeDetailsResponse>, t: Throwable) {
                onResponse(ApiState.Failure("Network Error - ${t.message.toString()}"))
                Log.e("Network Error", t.message.toString())
            }
        })
    }
}