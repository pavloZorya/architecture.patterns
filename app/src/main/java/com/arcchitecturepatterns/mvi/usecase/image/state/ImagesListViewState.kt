package com.arcchitecturepatterns.mvi.usecase.image.state

import com.arcchitecturepatterns.common.data.image.Image

sealed class ImagesListViewState {
    class ImagesLoadingNotStartedYet : ImagesListViewState()

    class Loading : ImagesListViewState()

    class EmptyResult : ImagesListViewState()

    data class ImagesLoadedViewState(val images: List<Image>) : ImagesListViewState()

    data class Error(val error: Throwable) : ImagesListViewState()
}