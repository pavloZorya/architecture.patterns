package com.arcchitecturepatterns.common.framework.database.saved

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arcchitecturepatterns.common.domain.entities.saved.SavedAtDomainModel

@Entity(tableName = "SavedAt")
class SavedAtDaoModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "saved_at")
    val savedAt: Long
)

fun SavedAtDaoModel.toDomainModel(): SavedAtDomainModel {
    return SavedAtDomainModel(savedAt, id)
}

fun List<SavedAtDaoModel>.toDomainModel(): List<SavedAtDomainModel> {
    return map { it.toDomainModel() }
}

fun SavedAtDomainModel.toDaoModel(): SavedAtDaoModel {
    return SavedAtDaoModel(id, savedAt)
}