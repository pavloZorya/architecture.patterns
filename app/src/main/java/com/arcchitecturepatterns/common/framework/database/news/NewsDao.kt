package com.arcchitecturepatterns.common.framework.database.news

import androidx.room.*
import com.arcchitecturepatterns.common.data.news.NewsRepository
import com.arcchitecturepatterns.common.domain.entities.news.NewsDomainModel

@Dao
interface NewsDao {

    @Query("SELECT * FROM NewsDaoModel WHERE id = :data")
    suspend fun get(data: Int): NewsDaoModel?

    @Query("SELECT * FROM NewsDaoModel")
    suspend fun getList(): List<NewsDaoModel>

    @Query("SELECT * FROM NewsDaoModel WHERE saved_at = :savedId")
    suspend fun getListBySavedId(savedId: Int): List<NewsDaoModel>?

    @Insert
    suspend fun insertData(news: NewsDaoModel)

    @Update
    suspend fun updateData(news: NewsDaoModel)

    @Delete
    suspend fun deleteData(news: NewsDaoModel)

    @Query("DELETE FROM NewsDaoModel")
    suspend fun clean()

}