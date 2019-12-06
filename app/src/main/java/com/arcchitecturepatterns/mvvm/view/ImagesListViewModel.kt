package com.arcchitecturepatterns.mvvm.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcchitecturepatterns.common.data.image.Image
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class ImagesListViewModel(private val interactor: ImagesInteractor = ImagesInteractor()) :
    ViewModel() {

    val imagesList = MutableLiveData<List<Image>>()
    val isLoading = MutableLiveData<Boolean>()
    var error = MutableLiveData<String>()

    private val errorHandler =
        CoroutineExceptionHandler { _, exception ->
            run {
                error.value = exception.message ?: "Error occurred"
                isLoading.value = false
            }
        }

    fun loadImages() {
        isLoading.value = true
        viewModelScope.launch(errorHandler) {
            imagesList.value = interactor.listImages()
            isLoading.value = false

        }
    }

}