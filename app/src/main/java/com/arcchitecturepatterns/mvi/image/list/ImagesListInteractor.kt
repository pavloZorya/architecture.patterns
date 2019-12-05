package com.arcchitecturepatterns.mvi.image.list

import com.arcchitecturepatterns.mvi.usecase.image.ImagesListUseCase
import com.arcchitecturepatterns.mvi.usecase.image.state.ImagesListViewState
import io.reactivex.Observable

class ImagesListInteractor(private val useCase: ImagesListUseCase = ImagesListUseCase()) {
    fun listImages(): Observable<ImagesListViewState> {
        return useCase.imagesList().map {
            if (it.isEmpty()) {
                return@map ImagesListViewState.EmptyResult()
            } else {
                return@map ImagesListViewState.ImagesLoadedViewState(it)
            }
        }
            .startWith(ImagesListViewState.Loading())
            .onErrorReturn { error -> ImagesListViewState.Error(error) }
    }
}