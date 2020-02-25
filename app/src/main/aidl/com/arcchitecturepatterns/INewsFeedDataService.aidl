// INewsFeedDataService.aidl
package com.arcchitecturepatterns;

import com.arcchitecturepatterns.common.framework.remote.news.service.data.NewsRemoteRequestListener;

// Declare any non-default types here with import statements

interface INewsFeedDataService {
    void news(NewsRemoteRequestListener listener);
}
