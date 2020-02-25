package com.arcchitecturepatterns.common.framework.remote.news.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.arcchitecturepatterns.INewsFeedDataService
import com.arcchitecturepatterns.common.framework.remote.api.ApiClient
import com.arcchitecturepatterns.common.framework.remote.news.service.data.NewsRemoteApiClass
import com.arcchitecturepatterns.common.framework.remote.news.service.data.NewsRemoteRequestListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class NewsFeedDataService : Service() {

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    private val binder = object : INewsFeedDataService.Stub() {

        override fun news(listener: NewsRemoteRequestListener?) {
            Timber.d("start loading news")
            ApiClient.client.getKittens().enqueue(
                object : Callback<NewsRemoteApiClass> {
                    override fun onFailure(call: Call<NewsRemoteApiClass>, t: Throwable) {
                        Timber.d("Error: ${t.javaClass.name}, ${t.message}")
                        listener?.onError()
                    }

                    override fun onResponse(
                        call: Call<NewsRemoteApiClass>,
                        response: Response<NewsRemoteApiClass>
                    ) {
                        Timber.d("OnSuccess")
                        val dataContainer = response.body()?.container?.dataContainer?.map { it.model }

                        listener?.onSuccess(dataContainer ?: emptyList())
                    }

                })
        }

    }
}
