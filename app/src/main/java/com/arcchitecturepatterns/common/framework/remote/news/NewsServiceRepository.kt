package com.arcchitecturepatterns.common.framework.remote.news

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import com.arcchitecturepatterns.INewsFeedDataService
import com.arcchitecturepatterns.common.data.news.NewsRepository
import com.arcchitecturepatterns.common.domain.entities.news.NewsDomainModel
import com.arcchitecturepatterns.common.framework.remote.news.service.*
import com.arcchitecturepatterns.common.framework.remote.news.service.data.NewsRemoteModel
import com.arcchitecturepatterns.common.framework.remote.news.service.data.NewsRemoteRequestListener
import com.arcchitecturepatterns.common.framework.remote.news.service.data.toDomainModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.NullPointerException
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NewsServiceRepository(private val context: Context) : NewsRepository,
    CoroutineScope by MainScope() {

    private var mService: INewsFeedDataService? = null
    private var connection: SuspendedServiceConnectionListener? = null

    private var connecting = false

    init {
        launch(Dispatchers.Main) {
            bind()
        }
    }

    private suspend fun bind() {
        connecting = true

        val intent = Intent(context, NewsFeedDataService::class.java)
        intent.action = INewsFeedDataService::class.java.name

        mService = suspendCoroutine<INewsFeedDataService> { continuation ->
            connection = SuspendedServiceConnectionListener(
                continuation
            ).also {
                context.bindService(
                    intent,
                    it,
                    Context.BIND_AUTO_CREATE
                )
                connecting = false
            }

        }
    }

    override suspend fun unbind() {
        Timber.d("unbind for service is invoked")
        connection?.let {
            suspendCoroutine<INewsFeedDataService> { cont ->
                it.continuation = cont
                context.unbindService(it)
                mService = null
            }
        } ?: run {
            Timber.d("connection was not established before")
        }
    }

    override suspend fun get(id: Int): NewsDomainModel? {
        return null
    }

    override suspend fun getList(): List<NewsDomainModel>? {
        Timber.d("getList")
        suspendTillConnected()
        return suspendCoroutine<List<NewsDomainModel>> { cont ->
            mService?.news(
                object : NewsRemoteRequestListener.Stub() {
                    override fun onSuccess(result: List<NewsRemoteModel>) {
                        cont.resume(result.toDomainModel())
                    }

                    override fun onError() {
                        cont.resumeWithException(NullPointerException())
                    }
                })
        }
    }

    override suspend fun getListBySavedId(savedId: Int): List<NewsDomainModel>? {
        return emptyList()
    }

    override suspend fun insertData(news: NewsDomainModel) {
    }

    override suspend fun updateData(news: NewsDomainModel) {
    }

    override suspend fun deleteData(news: NewsDomainModel) {
    }

    override suspend fun clean() {
    }

    private suspend fun suspendTillConnected() {
        if (mService == null && !connecting) {
            Timber.d("wait until connection established")
            bind()
            Timber.d("connection established continue...")
            return
        } else {
            Timber.d("connection established")
            return
        }
    }

    class SuspendedServiceConnectionListener(var continuation: Continuation<INewsFeedDataService>) :
        ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            Timber.d("onServiceConnected: ${className.className}")
            val mService = INewsFeedDataService.Stub.asInterface(service)

            continuation.resume(mService)
        }

        override fun onServiceDisconnected(className: ComponentName) {
            Timber.d("onServiceDisconnected")
            continuation.resumeWithException(RemoteException())

        }
    }
}
