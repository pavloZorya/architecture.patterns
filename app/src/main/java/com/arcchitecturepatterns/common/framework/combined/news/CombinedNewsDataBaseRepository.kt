package com.arcchitecturepatterns.common.framework.combined.news

import com.arcchitecturepatterns.common.data.news.NewsRepository
import com.arcchitecturepatterns.common.data.saved.SavedAtRepository
import com.arcchitecturepatterns.common.domain.entities.news.NewsDomainModel
import com.arcchitecturepatterns.common.domain.entities.saved.SavedAtDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class CombinedNewsDataBaseRepository(
    private val savedAtRepository: SavedAtRepository,
    private val localRepository: NewsRepository,
    private val remoteRepository: NewsRepository
) : NewsRepository {

    override suspend fun get(id: Int): NewsDomainModel? {
        return null
    }

    override suspend fun getList(): List<NewsDomainModel>? {
        val notExpiredDataSavedAtList = savedAtRepository.getList()?.filter { it ->
            Timber.d("savedAtTime: ${it.savedAt}")
            val savedAtTime = it.savedAt - ((1000 * 60 * 60 * 24) * 2)
            Date(savedAtTime).after(Calendar.getInstance().time)
        }

        return if (notExpiredDataSavedAtList.isNullOrEmpty()) {
            val list = getFromRemote()
            cacheList(list)
            list
        } else {
            getFromLocal(notExpiredDataSavedAtList)
        }
    }

    override suspend fun getListBySavedId(savedId: Int): List<NewsDomainModel>? {
        Timber.d("getListBySavedId: $savedId")
        return localRepository.getListBySavedId(savedId)
    }

    override suspend fun insertData(news: NewsDomainModel) {
        Timber.d("insertData: $news")
        localRepository.insertData(news)
    }

    override suspend fun updateData(news: NewsDomainModel) {
        Timber.d("updateData: $news")
        localRepository.updateData(news)
        remoteRepository.updateData(news)
    }

    override suspend fun deleteData(news: NewsDomainModel) {
        Timber.d("deleteData: $news")
        localRepository.deleteData(news)
        remoteRepository.deleteData(news)
    }

    override suspend fun clean() {
        Timber.d("clean")
        localRepository.clean()
        savedAtRepository.clean()
        remoteRepository.clean()
    }

    override suspend fun unbind() {
        Timber.d("unbind")
        remoteRepository.unbind()
        localRepository.unbind()
    }

    private suspend fun getFromRemote(): List<NewsDomainModel>? {
        withContext(Dispatchers.IO) {
            Timber.d("has no appropriate data")
            clean()
        }
        return remoteRepository.getList()
    }

    private suspend fun cacheList(
        list: List<NewsDomainModel>?
    ) {
        Timber.d("data to cache: ${list?.size}")
        savedAtRepository.insertData(SavedAtDomainModel(System.currentTimeMillis()))
        savedAtRepository.getList()
            ?.last()
            ?.also {
                Timber.d("data from db is not null. cache it")
            }
            ?.let { savedAt ->
                Timber.d("cache data for saved time: ${list?.size}")
                list?.forEach {
                    it.savedAt = savedAt.id
                    insertData(it)
                }
            }
    }

    private suspend fun getFromLocal(
        notExpiredDataSavedAtList: List<SavedAtDomainModel>
    ): ArrayList<NewsDomainModel> {

        Timber.d("has some data in cache")

        val resultList = arrayListOf<NewsDomainModel>()
        notExpiredDataSavedAtList
            .map { getListBySavedId(it.id) }
            .also {
                Timber.d("data: $it")
            }
            .forEach {
                it?.let {
                    resultList.addAll(it)
                }
            }
        Timber.d("resultList: ${resultList.size}")
        return resultList
    }

}