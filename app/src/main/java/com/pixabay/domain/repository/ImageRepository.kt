package com.pixabay.domain.repository

import com.pixabay.domain.model.Images

interface ImageRepository {

    suspend fun getImages(url : String) : Images

//    suspend fun insertImages(list: List<Images>)
}