package com.arcchitecturepatterns.common.framework.database.news

import com.arcchitecturepatterns.common.data.news.NewsRepository
import com.arcchitecturepatterns.common.domain.entities.news.NewsDomainModel

class NewsDataBaseRepository(private val dao: NewsDao) : NewsRepository {
    override suspend fun get(id: Int): NewsDomainModel? {
        return dao.get(id)?.toDomainModel()
    }

    override suspend fun getList(): List<NewsDomainModel>? {
        return dao.getList().toDomainModel()
    }

    override suspend fun getListBySavedId(savedId: Int): List<NewsDomainModel>? {
        return dao.getListBySavedId(savedId)?.toDomainModel()
    }

    override suspend fun insertData(news: NewsDomainModel) {
        dao.insertData(news.toDaoModel())
    }

    override suspend fun updateData(news: NewsDomainModel) {
        dao.updateData(news.toDaoModel())
    }

    override suspend fun deleteData(news: NewsDomainModel) {
        dao.deleteData(news.toDaoModel())
    }

    override suspend fun clean() {
        dao.clean()
    }

    override suspend fun unbind() {
    }
}