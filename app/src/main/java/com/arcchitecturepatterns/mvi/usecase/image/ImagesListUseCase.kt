package com.arcchitecturepatterns.mvi.usecase.image

import com.arcchitecturepatterns.mvi.data.image.Image
import com.arcchitecturepatterns.mvi.data.source.ImagesDataSource
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class ImagesListUseCase {
    fun imagesList(): Observable<List<Image>> {
        return Observable.just(ImagesDataSource.images).delay(2000, TimeUnit.MILLISECONDS)
    }
}