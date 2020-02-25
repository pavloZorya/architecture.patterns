package com.arcchitecturepatterns.mvvm.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcchitecturepatterns.BuildConfig
import com.arcchitecturepatterns.common.usacase.news.GetNews
import com.arcchitecturepatterns.mvvm.view.data.NewsModel
import kotlinx.coroutines.*
import timber.log.Timber

class NewsFeedViewModel(private val getNews: GetNews<List<NewsModel>>) :
    ViewModel() {

    val newsList = MutableLiveData<List<NewsModel>>()
    val isLoading = MutableLiveData<Boolean>()
    var error = MutableLiveData<String>()

    private val errorHandler =
        CoroutineExceptionHandler { _, exception ->
            run {
                var message = exception.message ?: "Error occurred"
                if (BuildConfig.DEBUG) {
                    message += exception::class.java.name
                }
                error.value = message
                isLoading.value = false
            }
        }

    fun loadNews() {
        isLoading.value = true
        viewModelScope.launch(errorHandler) {
            newsList.value = getNews()
            isLoading.value = false

        }
    }

    override fun onCleared() {
        Timber.d("onCleared invoked")
        MainScope().launch {
            getNews.unbind()
        }
        super.onCleared()
    }

}