package com.devikiran.seekhoassignment.util

import com.devikiran.seekhoassignment.data.Anime
import com.devikiran.seekhoassignment.data.AnimeDetails

sealed class ApiState {
    data class TopAnimeSuccess(val data: List<Anime>) : ApiState()
    data class DetailedAnimeSuccess(val data: AnimeDetails) : ApiState()
    data class Failure(val msg: String) : ApiState()
    data object Loading:ApiState()
    data object Empty: ApiState()
}
