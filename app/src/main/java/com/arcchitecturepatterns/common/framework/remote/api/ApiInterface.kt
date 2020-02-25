package com.arcchitecturepatterns.common.framework.remote.api

import com.arcchitecturepatterns.common.framework.remote.news.service.data.NewsRemoteApiClass
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("/r/prics/search.json?q=kittens&sort=new")
    fun getKittens() : Call<NewsRemoteApiClass>
}