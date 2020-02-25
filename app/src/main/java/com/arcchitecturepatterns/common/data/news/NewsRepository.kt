package com.arcchitecturepatterns.common.data.news

import com.arcchitecturepatterns.common.domain.entities.news.NewsDomainModel

interface NewsRepository {
    suspend fun get(id: Int): NewsDomainModel?

    suspend fun getList(): List<NewsDomainModel>?

    suspend fun getListBySavedId(savedId: Int): List<NewsDomainModel>?

    suspend fun insertData(news: NewsDomainModel)

    suspend fun updateData(news: NewsDomainModel)

    suspend fun deleteData(news: NewsDomainModel)

    suspend fun clean()

    suspend fun unbind()
}