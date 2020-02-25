package com.arcchitecturepatterns.common.framework.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arcchitecturepatterns.common.framework.database.saved.SavedAtDao
import com.arcchitecturepatterns.common.framework.database.saved.SavedAtDaoModel
import com.arcchitecturepatterns.common.framework.database.news.NewsDao
import com.arcchitecturepatterns.common.framework.database.news.NewsDaoModel

@Database(
    entities = [NewsDaoModel::class, SavedAtDaoModel::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDataBase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
    abstract fun savedAtDao(): SavedAtDao

    companion object {
        @Volatile
        private var INSTANCE: NewsDataBase? = null

        fun getDatabase(): NewsDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            } else {
                throw RuntimeException("DB is not initialized")
            }
        }

        fun initialize(context: Context) {
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDataBase::class.java,
                    "news_feed_database"
                ).build()
                INSTANCE = instance
            }
        }
    }
}