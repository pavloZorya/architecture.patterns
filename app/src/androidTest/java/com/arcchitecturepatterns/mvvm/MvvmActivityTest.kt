package com.arcchitecturepatterns.mvvm

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.arcchitecturepatterns.BuildConfig
import com.arcchitecturepatterns.R
import com.arcchitecturepatterns.common.data.news.NewsRepository
import com.arcchitecturepatterns.common.domain.entities.news.NewsDomainModel
import com.arcchitecturepatterns.common.usacase.news.GetNews
import com.arcchitecturepatterns.mvvm.view.NewsFeedFragment
import com.arcchitecturepatterns.mvvm.view.NewsFeedViewModel
import com.arcchitecturepatterns.mvvm.view.data.ToNewsModelListTransformer
import com.arcchitecturepatterns.mvvm.view.data.ToNewsModelTransformer
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import org.hamcrest.BaseMatcher
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Description
import org.hamcrest.Matchers.endsWith
import org.junit.After
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test

class MvvmActivityTest {

    @Rule
    @JvmField
    var mActivityRule: ActivityTestRule<MvvmActivity> = ActivityTestRule(
        MvvmActivity::class.java
    )

    companion object {
        @BeforeClass
        @JvmStatic
        fun prepare() {

            val repository = mockk<NewsRepository>()
            coEvery { repository.getList() } returns emptyList()
            val transformer = ToNewsModelListTransformer(ToNewsModelTransformer())

            mockkObject(NewsFeedFragment.VModelProvider)
            every {
                NewsFeedFragment.VModelProvider.prepareNewsFeedViewModel(
                    any(),
                    any()
                )
            } returns NewsFeedViewModel(
                GetNews(repository, transformer)
            )
        }
    }

    @Test
    fun checkIfTitleIsAppropriate() {
        onView(withText("MVVM")).check(matches(isDisplayed()))
    }

    @Test
    fun checkIfRecyclerViewIsDisplayed() {
        replaceRepository(repositoryWithTestData()).viewModel.loadNews()
        onView(
            withClassName(
                endsWith(
                    "RecyclerView"
                )
            )
        ).check(
            matches(
                withId(R.id.recyclerView)
            )
        ).check(matches(withListSize(3)))
            .also {
            onView(withId(R.id.errorMessage)).check(matches(not(isDisplayed())))
            onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
        }
    }

    @After
    @Test
    fun checkShowEmptyDataIfThereAreNoContent() {
        replaceRepository(repositoryWithNoData()).viewModel.loadNews()

        onView(
            withId(R.id.errorMessage)
        ).check(
            matches(
                withText("There are no results")
            )
        ).also {
            onView(withId(R.id.recyclerView)).check(matches(not(isDisplayed())))
            onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
        }
    }


    @Test
    fun checkWithException() {
        val errorMessage = "error occurred"
        replaceRepository(repositoryWithException(errorMessage)).viewModel.loadNews()

        onView(
            withId(R.id.errorMessage)
        ).check(
            matches(
                if (BuildConfig.DEBUG) {
                    withText("$errorMessage, java.lang.NullPointerException")
                } else {
                    withText(errorMessage)
                }
            )
        ).also {
            onView(withId(R.id.recyclerView)).check(matches(not(isDisplayed())))
            onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
        }
    }

    private fun replaceRepository(repository: NewsRepository): NewsFeedFragment {
        val findFragmentById =
            mActivityRule
                .activity
                .supportFragmentManager
                .findFragmentById(R.id.imagesList) as NewsFeedFragment

        findFragmentById.viewModel.getNews = GetNews(
            repository,
            ToNewsModelListTransformer(ToNewsModelTransformer())
        )
        return findFragmentById
    }

    private fun repositoryWithTestData(): NewsRepository {
        return mockk<NewsRepository>().apply {
            coEvery { getList() } returns arrayListOf(
                NewsDomainModel("1", "1", "1"),
                NewsDomainModel("2", "2", "2"),
                NewsDomainModel("3", "3", "3")
            )
            coEvery { unbind() } answers {}
        }
    }

    private fun repositoryWithException(message: String): NewsRepository {
        return mockk<NewsRepository>().apply {
            coEvery { getList() } answers { throw NullPointerException(message) }
            coEvery { unbind() } answers {}
        }
    }

    private fun repositoryWithNoData(): NewsRepository {
        return mockk<NewsRepository>().apply {
            coEvery { getList() } returns emptyList()
            coEvery { unbind() } answers {}
        }
    }

    private fun withListSize(size: Int): BaseMatcher<View> {
        return object : BaseMatcher<View>() {

            override fun describeTo(description: Description) {
                description.appendText("RecyclerView should have $size items")
            }

            override fun matches(item: Any?): Boolean {
                val recyclerView = item as RecyclerView
                return recyclerView.adapter?.itemCount == size
            }
        }
    }


}

