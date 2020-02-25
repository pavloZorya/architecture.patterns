package com.arcchitecturepatterns.common.usacase.news

import com.arcchitecturepatterns.common.data.news.NewsRepository
import com.arcchitecturepatterns.common.domain.entities.news.NewsDomainModel

class GetNews<To : Any>(
    private val repository: NewsRepository,
    private val transform: Transformer<List<NewsDomainModel>, To>
) : UseCase<List<NewsDomainModel>, To>(transform) {

    suspend operator fun invoke(): To? {
        return repository.getList()?.let {
            transform(it)
        } ?: kotlin.run { null }
    }

    suspend fun unbind() {
        repository.unbind()
    }
}