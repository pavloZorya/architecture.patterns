package com.arcchitecturepatterns.mvp.view

import android.annotation.SuppressLint
import android.util.Log
import com.arcchitecturepatterns.mvi.usecase.image.ImagesListUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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
                Log.d("ImagesListPresenter", "images loaded: ${it.size}")
                if (it.isEmpty()) {
                    view.showEmptyResult()
                } else {
                    view.showImages(it)
                }
            }
            .startWith(view.showLoading())
            .onErrorReturn { error -> view.showError(error.localizedMessage) }
            .subscribe()
    }

}