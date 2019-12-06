package com.arcchitecturepatterns.mvvm.view

import com.arcchitecturepatterns.common.data.image.Image
import com.arcchitecturepatterns.common.data.source.ImagesDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ImagesInteractor {
    suspend fun listImages(): List<Image> = withContext(Dispatchers.IO) {
        delay(2000)
        return@withContext ImagesDataSource.images
    }
}
