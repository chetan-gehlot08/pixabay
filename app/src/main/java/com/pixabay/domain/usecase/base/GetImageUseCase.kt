package com.pixabay.domain.usecase.base

import com.pixabay.domain.model.Images
import com.pixabay.domain.repository.ImageRepository

class GetImageUseCase(
    private val imageRepository: ImageRepository,
) : SingleUseCase<Images>() {

    private lateinit var url: String

    fun setUrl(url: String) {
        this.url = url
    }

    override suspend fun buildUseCaseSingle(): Images {
        return imageRepository.getImages(url)
    }
}