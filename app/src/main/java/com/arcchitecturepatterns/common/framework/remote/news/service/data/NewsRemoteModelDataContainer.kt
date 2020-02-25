package com.arcchitecturepatterns.common.framework.remote.news.service.data

import com.google.gson.annotations.SerializedName

class NewsRemoteModelDataContainer(
    @SerializedName("data")
    val model: NewsRemoteModel
)
