package com.arcchitecturepatterns.mvp.view

import android.annotation.SuppressLint
import com.arcchitecturepatterns.mvi.usecase.image.ImagesListUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ImagesListPresenter(
    private val view: ImagesListContract.ImagesListView,
    private val useCase: ImagesListUseCase = ImagesListUseCase()
) :
    ImagesListContract.ImagesListPresenter {
    @SuppressLint("CheckResult")
    override fun loadImages() {
        useCase.imagesList()
            .subscribeOn(
                Schedulers.io()
            ).observeOn(AndroidSchedulers.mainThread())
            .map {
                Timber.d("images loaded: ${it.size}")
                if (it.isEmpty()) {
                    view.showEmptyResult()
                } else {
                    view.showImages(it)
                }
            }
            .startWith(view.showLoading())
            .onErrorReturn { error ->
                run {
                    Timber.d("error: ${error.message}")
                    view.showError(error?.localizedMessage ?: "Error is null")
                }
            }
            .subscribe()
    }

}