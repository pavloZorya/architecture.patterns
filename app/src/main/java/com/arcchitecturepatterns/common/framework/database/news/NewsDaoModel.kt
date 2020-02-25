package com.arcchitecturepatterns.common.framework.database.news

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.arcchitecturepatterns.common.domain.entities.news.NewsDomainModel
import com.arcchitecturepatterns.common.framework.database.saved.SavedAtDaoModel

@Entity(tableName = "NewsDaoModel")
data class NewsDaoModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    val title: String,
    val description: String,
    val link: String,

    @ForeignKey(
        entity = SavedAtDaoModel::class,
        parentColumns = ["id"],
        childColumns = ["saved_at"],
        onDelete = ForeignKey.CASCADE
    )
    @ColumnInfo(name = "saved_at")
    val savedAt: Int
)

fun NewsDomainModel.toDaoModel(): NewsDaoModel {
    return NewsDaoModel(id, title ?: "", description ?: "", link ?: "", savedAt)
}

fun NewsDaoModel.toDomainModel(): NewsDomainModel {
    return NewsDomainModel(title, description, link, savedAt, id)
}

fun List<NewsDaoModel>.toDomainModel(): List<NewsDomainModel> {
    return map { it.toDomainModel() }
}