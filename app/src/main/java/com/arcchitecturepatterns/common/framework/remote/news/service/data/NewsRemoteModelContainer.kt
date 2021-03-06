package com.arcchitecturepatterns.common.framework.remote.news.service.data

import com.google.gson.annotations.SerializedName

class NewsRemoteModelContainer(
    @SerializedName("children")
    val dataContainer: List<NewsRemoteModelDataContainer>
)
