package com.arcchitecturepatterns.common.framework.remote.news.service.data

import com.arcchitecturepatterns.common.framework.remote.news.service.data.NewsRemoteModel
import com.google.gson.annotations.SerializedName

class NewsRemoteModelContainer(
    @SerializedName("children")
    val models: List<NewsRemoteModel>
)
