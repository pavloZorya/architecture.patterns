package com.arcchitecturepatterns.common.data.saved

import com.arcchitecturepatterns.common.domain.entities.saved.SavedAtDomainModel

interface SavedAtRepository {
    suspend fun get(id: Int): SavedAtDomainModel?

    suspend fun getList(): List<SavedAtDomainModel>?

    suspend fun insertData(savedAt: SavedAtDomainModel)

    suspend fun updateData(savedAt: SavedAtDomainModel)

    suspend fun deleteData(savedAt: SavedAtDomainModel)
}