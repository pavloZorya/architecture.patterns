package com.arcchitecturepatterns.mvvm.view.data

import com.arcchitecturepatterns.common.domain.entities.news.NewsDomainModel
import com.arcchitecturepatterns.common.usacase.news.Transformer


data class NewsModel(val id: Int, val title: String, val description: String, val imageUrl: String)

class ToNewsModelTransformer :
    Transformer<NewsDomainModel, NewsModel> {
    override fun invoke(data: NewsDomainModel): NewsModel {
        return data.parseToNewsModel()
    }
}

fun NewsDomainModel.parseToNewsModel(): NewsModel {
    return NewsModel(this.id, this.title ?: "", this.description ?: "", this.link ?: "")
}


class ToNewsModelListTransformer(private val transformer: ToNewsModelTransformer) :
    Transformer<List<NewsDomainModel>, List<NewsModel>> {
    override fun invoke(data: List<NewsDomainModel>): List<NewsModel> {
        return data.parseToNewsModels(transformer)
    }
}

fun List<NewsDomainModel>.parseToNewsModels(transformer: ToNewsModelTransformer): List<NewsModel> {
    return this.map { transformer.invoke(it) }
}