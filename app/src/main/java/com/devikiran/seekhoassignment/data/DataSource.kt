package com.devikiran.seekhoassignment.data

data class AnimeDetailsResponse(
    val data: AnimeDetails
)

data class AnimeDetails(
    val mal_id: Int,
    val title: String,
    val synopsis: String,
    val genres: List<Genre>,
    val trailer: Trailer?,
    val episodes: Int?,
    val score: Double?,
    val images: Images
)

data class Genre(
    val name: String
)

data class Trailer(
    val url: String?
)




data class TopAnimeResponse(
    val data: List<Anime>
)

data class Anime(
    val mal_id: Int,
    val title: String,
    val episodes: Int?,
    val score: Double?,
    val images: Images
)

data class Images(
    val jpg: JpgImage
)

data class JpgImage(
    val image_url: String
)