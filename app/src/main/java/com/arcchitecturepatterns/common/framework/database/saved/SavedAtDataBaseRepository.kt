package com.arcchitecturepatterns.common.framework.database.saved

import com.arcchitecturepatterns.common.data.saved.SavedAtRepository
import com.arcchitecturepatterns.common.domain.entities.saved.SavedAtDomainModel

class SavedAtDataBaseRepository(private val dao: SavedAtDao) : SavedAtRepository {
    override suspend fun get(id: Int): SavedAtDomainModel? {
        return dao.get(id)?.toDomainModel()
    }

    override suspend fun getList(): List<SavedAtDomainModel>? {
        return dao.getList().toDomainModel()
    }

    override suspend fun insertData(savedAt: SavedAtDomainModel) {
        dao.insertData(savedAt.toDaoModel())
    }

    override suspend fun updateData(savedAt: SavedAtDomainModel) {
        dao.updateData(savedAt.toDaoModel())
    }

    override suspend fun deleteData(savedAt: SavedAtDomainModel) {
        dao.deleteData(savedAt.toDaoModel())
    }
}