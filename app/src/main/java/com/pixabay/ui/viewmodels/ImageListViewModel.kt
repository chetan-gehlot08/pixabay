package com.pixabay.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pixabay.domain.model.Images
import com.pixabay.domain.usecase.base.GetImageUseCase
import com.pixabay.util.CloudErrorMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val getImageUseCase: GetImageUseCase
) : ViewModel() {
    var isLoad = MutableLiveData<Boolean>()
    var imageListLiveData = MutableLiveData<Images>()
    var errorLiveData = MutableLiveData<String>()

    fun getImages(searchKey: String) {
        val imageUrl =
            "/api/?key=21458732-cd11c7fdad8c4d6fb275e64b4&q=$searchKey&image_type=photo"
        isLoad.value = true
        viewModelScope.launch {
            getImageUseCase.setUrl(imageUrl)
            getImageUseCase.execute(onError = {
                it?.printStackTrace()
                errorLiveData.value = CloudErrorMapper.mapToDomainErrorException(it)
            }, onSuccess = {
                imageListLiveData.postValue(it)
                Log.d("MainViewModel", it.toString())
            })
        }
    }
}