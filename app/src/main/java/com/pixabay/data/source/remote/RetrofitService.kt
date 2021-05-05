package com.pixabay.data.source.remote

import com.pixabay.domain.model.Images
import retrofit2.http.GET
import retrofit2.http.Url

interface RetrofitService {

    @GET
    suspend fun getImages(@Url url : String): Images
}