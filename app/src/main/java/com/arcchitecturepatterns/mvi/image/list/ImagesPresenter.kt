package com.arcchitecturepatterns.mvi.image.list

import com.arcchitecturepatterns.mvi.usecase.image.state.ImagesListViewState
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers

class ImagesPresenter(private val interactor: ImagesListInteractor) :
    MviBasePresenter<ImagesListView, ImagesListViewState>(ImagesListViewState.ImagesLoadingNotStartedYet()) {
    override fun bindIntents() {
        val observableViewState = intent(ImagesListView::loadListIntent)
            .switchMap { interactor.listImages() }
            .observeOn(AndroidSchedulers.mainThread())
        subscribeViewState(observableViewState, ImagesListView::render)
    }
}