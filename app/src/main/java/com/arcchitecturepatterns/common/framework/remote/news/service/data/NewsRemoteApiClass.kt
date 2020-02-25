package com.arcchitecturepatterns.common.framework.remote.news.service.data

import com.google.gson.annotations.SerializedName

class NewsRemoteApiClass(
    @SerializedName("data")
    val container: NewsRemoteModelContainer
)
