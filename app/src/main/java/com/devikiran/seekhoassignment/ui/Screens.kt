package com.devikiran.seekhoassignment.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.devikiran.seekhoassignment.MainViewModel
import com.devikiran.seekhoassignment.R
import com.devikiran.seekhoassignment.data.Anime
import com.devikiran.seekhoassignment.data.AnimeDetails
import com.devikiran.seekhoassignment.util.ApiState
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


@Composable
fun GETData(mainViewModel: MainViewModel) {
    when (val result = mainViewModel.response.value) {
        is ApiState.TopAnimeSuccess -> {
            LazyColumn {
                items(result.data) { response ->
                    TopAnimeItem(data = response){
                        mainViewModel.fetchAnimeDetails(it)
                    }
                }
            }
        }
        is ApiState.DetailedAnimeSuccess -> {
            DetailAnimItem(result.data) {
                mainViewModel.navigateToTopAnime()
            }
        }

        is ApiState.Failure -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = result.msg)
            }
        }

        ApiState.Loading -> {
            LoadingScreen()
        }

        ApiState.Empty -> {

        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(40.dp)
        )
    }
}

@Composable
fun TopAnimeItem(data: Anime, onResponse: (Int) -> Unit){

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
            .clickable {
                onResponse(data.mal_id)
            }
    ){

        Row (
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Gray.copy(alpha = 0.5f))
                ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Start)
        ) {

            AsyncImage(
                model = data.images.jpg.image_url,
                contentDescription = "Fetched Image",
                modifier = Modifier,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_loading),
                error = painterResource(R.drawable.ic_error)
            )

            Column (
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = data.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Row (
                    horizontalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Start)
                ) {

                    Text(
                        text = "Episode Number ${data.episodes}",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )

                    Text(
                        text = "Ratings ${data.score}",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}


@Composable
fun DetailAnimItem(animeDetails: AnimeDetails, onResponse: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Start)
            ) {
                IconButton(
                    onClick = {
                        onResponse()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }


            if(animeDetails.trailer != null) {
                val videoId = animeDetails.trailer.url.toString().substringAfter("=")

                AndroidView(factory = {
                    val view = YouTubePlayerView(it)
                    view.addYouTubePlayerListener(
                        object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                super.onReady(youTubePlayer)
                                youTubePlayer.loadVideo(videoId, 0f)
                            }
                        }
                    )
                    view
                })
            }else{

                AsyncImage(
                    model = animeDetails.images.jpg.image_url,
                    contentDescription = "Fetched Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.ic_loading),
                    error = painterResource(R.drawable.ic_error)
                )
            }

            LazyColumn (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterVertically)
            ) {
                item {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Top)
                    ) {
                        Text(
                            modifier = Modifier.padding(top = 8.dp),
                            text = animeDetails.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = Color.White
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(
                                8.dp,
                                alignment = Alignment.Start
                            )
                        ) {

                            Text(
                                text = "Episodes - ${animeDetails.episodes}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color.White
                            )

                            Text(
                                text = " | ",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color.White
                            )




                            Text(
                                text = "Ratings - ${animeDetails.score}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }
                    }
                }

                item {
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = "Genres",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start)
                    ) {
                        items(animeDetails.genres) { label ->
                            AssistChip(
                                onClick = {

                                },
                                label = {
                                    Text(
                                        text =  label.name,
                                        fontSize = 13.sp
                                    )
                                }
                            )
                        }
                    }
                }

                item {

                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(
                            8.dp,
                            alignment = Alignment.CenterVertically
                        )
                    ) {
                        Text(
                            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                            text = "Synopsis",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )

                        Text(
                            modifier = Modifier,
                            text = animeDetails.synopsis,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
