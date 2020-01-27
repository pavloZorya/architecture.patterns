package com.arcchitecturepatterns.common.data.image

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

internal class ImageTest : StringSpec({
    "data passed into constructor are the same as data from getters" {
        val url = "testUrl"
        val title = "testTitle"
        val image = Image(url, title)

        image.link.shouldBe(url)
        image.title.shouldBe(title)
    }
})