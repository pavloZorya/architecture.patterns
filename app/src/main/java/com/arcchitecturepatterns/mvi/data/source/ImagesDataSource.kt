package com.arcchitecturepatterns.mvi.data.source

import com.arcchitecturepatterns.mvi.data.image.Image

object ImagesDataSource {
    val images: List<Image> by lazy {
        listOf(
            Image("https://cosmos-images2.imgix.net/file/spina/photo/20565/191010_nature.jpg", "Test title"),
            Image("https://www.medicalnewstoday.com/content/images/articles/325/325466/man-walking-dog.jpg", "Test title"),
            Image("http://i1.ytimg.com/vi/BHACKCNDMW8/maxresdefault.jpg", "Test title"),
            Image("https://natgeo.imgix.net/factsheets/thumbnails/01-balance-of-nature.adapt.jpg", "Test title"),
            Image("https://natureconservancy-h.assetsadobe.com/is/image/content/dam/tnc/nature/en/photos/tnc_69881045.jpg", "Test title"),
            Image("https://www.thinkright.me/wp-content/uploads/2019/09/Untitled-design-27.jpg", "Test title"),
            Image("https://c402277.ssl.cf1.rackcdn.com/photos/2842/images/blog_show/shutterstock_12730534.jpg", "Test title")
        )
    }
}