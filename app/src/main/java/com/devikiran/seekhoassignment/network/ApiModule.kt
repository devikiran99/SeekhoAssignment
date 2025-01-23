package com.devikiran.seekhoassignment.network

import com.devikiran.seekhoassignment.network.ApiService.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiModule {
    fun providesApiService(): ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

}