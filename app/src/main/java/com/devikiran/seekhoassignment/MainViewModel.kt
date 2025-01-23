package com.devikiran.seekhoassignment

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devikiran.seekhoassignment.network.ApiModule
import com.devikiran.seekhoassignment.network.ApiService
import com.devikiran.seekhoassignment.util.ApiState
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {
    private val mainRepository: MainRepository = MainRepository()
    val response: MutableState<ApiState> = mutableStateOf(ApiState.Empty)
    private val apiService: ApiService = ApiModule.providesApiService()

    init {
        fetchTopAnime()
    }

    private fun fetchTopAnime() {
        viewModelScope.launch {
            mainRepository.fetchTopAnime(apiService){
                response.value = it
            }
        }
    }

    fun fetchAnimeDetails(animeId: Int) {
        mainRepository.fetchAnimeDetails(apiService, animeId) {
            response.value = it
        }
    }

    fun navigateToTopAnime() {
        viewModelScope.launch {
            mainRepository.fetchTopAnime(apiService){
                response.value = it
            }
        }
    }

    fun closeScreen(){
        viewModelScope.launch {

        }
    }
}