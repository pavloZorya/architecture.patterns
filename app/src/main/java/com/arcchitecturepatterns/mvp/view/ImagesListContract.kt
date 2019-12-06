package com.arcchitecturepatterns.mvp.view

import com.arcchitecturepatterns.common.data.image.Image

interface ImagesListContract {
    interface ImagesListView {
        fun showImages(images: List<Image>)
        fun showEmptyResult()
        fun showLoading()
        fun showError(message: String)
    }
    interface ImagesListPresenter {
        fun loadImages()
    }
}