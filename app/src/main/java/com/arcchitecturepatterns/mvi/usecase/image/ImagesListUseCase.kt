package com.arcchitecturepatterns.mvi.usecase.image

import com.arcchitecturepatterns.common.data.image.Image
import com.arcchitecturepatterns.common.data.source.ImagesDataSource
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ImagesListUseCase {
    fun imagesList(): Observable<List<Image>> {
        return Observable.just(ImagesDataSource.images).delay(2000, TimeUnit.MILLISECONDS)
    }
}