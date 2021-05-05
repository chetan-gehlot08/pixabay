package com.pixabay.data.repository

import com.pixabay.data.source.remote.RetrofitService
import com.pixabay.domain.model.Images
import com.pixabay.domain.repository.ImageRepository

class ImageRepositoryImpl(private val retrofitHelper: RetrofitService) : ImageRepository {
    override suspend fun getImages(url : String): Images {
        return retrofitHelper.getImages(url)
    }
}