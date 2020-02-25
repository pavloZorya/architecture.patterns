package com.arcchitecturepatterns

import android.app.Application
import com.arcchitecturepatterns.common.framework.database.NewsDataBase

class NewsFeedApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        NewsDataBase.initialize(this)
    }
}