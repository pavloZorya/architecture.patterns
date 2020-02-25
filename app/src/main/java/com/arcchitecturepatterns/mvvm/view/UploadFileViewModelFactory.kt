package com.arcchitecturepatterns.mvvm.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arcchitecturepatterns.common.usacase.news.GetNews
import com.arcchitecturepatterns.mvvm.view.data.NewsModel

class UploadFileViewModelFactory(private val useCase: GetNews<List<NewsModel>>) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsFeedViewModel(useCase) as T
    }
}