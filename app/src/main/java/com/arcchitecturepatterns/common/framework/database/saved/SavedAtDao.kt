package com.arcchitecturepatterns.common.framework.database.saved

import androidx.room.*
import com.arcchitecturepatterns.common.data.saved.SavedAtRepository
import com.arcchitecturepatterns.common.domain.entities.saved.SavedAtDomainModel

@Dao
interface SavedAtDao {

    @Query("SELECT * FROM SavedAt WHERE id = :id")
    suspend fun get(id: Int): SavedAtDaoModel?

    @Query("SELECT * FROM SavedAt")
    suspend fun getList(): List<SavedAtDaoModel>

    @Insert
    suspend fun insertData(savedAt: SavedAtDaoModel)

    @Update
    suspend fun updateData(savedAt: SavedAtDaoModel)

    @Delete
    suspend fun deleteData(savedAt: SavedAtDaoModel)

    @Query("DELETE FROM SavedAt")
    fun clean()
}