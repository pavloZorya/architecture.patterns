package com.arcchitecturepatterns.mvi.view

import com.arcchitecturepatterns.mvi.usecase.image.state.ImagesListViewState
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface ImagesListView : MvpView {

    fun loadListIntent() : Observable<Boolean>

    fun render(viewState: ImagesListViewState)
}