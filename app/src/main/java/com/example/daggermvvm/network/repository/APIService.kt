package com.example.daggermvvm.network.repository

import com.example.daggermvvm.network.model.PostInfo
import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    @GET("posts")
    fun makeRequest(): Call<List<PostInfo>>
}