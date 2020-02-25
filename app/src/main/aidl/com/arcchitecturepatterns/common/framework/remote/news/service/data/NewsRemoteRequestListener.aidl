// NewsRemoteModel.aidl
package com.arcchitecturepatterns.common.framework.remote.news.service.data;
import com.arcchitecturepatterns.common.framework.remote.news.service.data.NewsRemoteModel;
//parcelable NewsRemoteRequestListener;

interface NewsRemoteRequestListener {
    void onSuccess(in List<NewsRemoteModel> data);
    void onError();
}