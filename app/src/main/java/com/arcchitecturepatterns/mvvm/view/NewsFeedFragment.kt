package com.arcchitecturepatterns.mvvm.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.arcchitecturepatterns.R
import com.arcchitecturepatterns.common.framework.combined.news.CombinedNewsDataBaseRepository
import com.arcchitecturepatterns.common.framework.database.NewsDataBase
import com.arcchitecturepatterns.common.framework.database.news.NewsDataBaseRepository
import com.arcchitecturepatterns.common.framework.database.saved.SavedAtDataBaseRepository
import com.arcchitecturepatterns.common.framework.remote.news.NewsServiceRepository
import com.arcchitecturepatterns.common.usacase.news.GetNews
import com.arcchitecturepatterns.common.view.NewsFeedRecyclerViewAdapter
import com.arcchitecturepatterns.mvvm.view.data.NewsModel
import com.arcchitecturepatterns.mvvm.view.data.ToNewsModelListTransformer
import com.arcchitecturepatterns.mvvm.view.data.ToNewsModelTransformer
import kotlinx.android.synthetic.main.fragment_images_list.*
import timber.log.Timber

class NewsFeedFragment : Fragment() {

    private var columnCount = 1
    lateinit var viewModel: NewsFeedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_images_list, container, false)
        viewModel = VModelProvider.prepareNewsFeedViewModel(this, context!!)
        this.initializeViewModelListeners(viewModel)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated")

        with(recyclerView) {
            Timber.d("set up recyclerView")
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
        }

        recyclerView.adapter =
            NewsFeedRecyclerViewAdapter(
                ArrayList()
            )

        initializeViewModelListeners(viewModel)
        viewModel.loadNews()
    }

    private fun initializeViewModelListeners(viewModel: NewsFeedViewModel) {
        Timber.d("initializeViewModelListeners")
        viewModel.newsList.observe(viewLifecycleOwner, Observer {
            Timber.d("images list size: ${it.size}")
            if (it.isNotEmpty()) {
                showImages(it)
            } else {
                showEmptyResult()
            }
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            Timber.d("isLoading: $it")
            if (it) {
                showLoading()
            }
        })
        viewModel.error.observe(viewLifecycleOwner, Observer {
            Timber.d("error: $it")
            showError(it)
        })
    }

    private fun showImages(newsList: List<NewsModel>) {
        Timber.d("showImages: ${newsList.size}")
        (recyclerView.adapter as NewsFeedRecyclerViewAdapter).setItems(newsList, 0)

        recyclerView.visibility = VISIBLE
        progressBar.visibility = GONE
        errorMessage.visibility = GONE
    }

    private fun showEmptyResult() {
        Timber.d("showEmptyResult")
        progressBar.visibility = GONE
        recyclerView.visibility = GONE
        errorMessage.visibility = VISIBLE
        errorMessage.text = "There are no results"
    }

    private fun showLoading() {
        Timber.d("showLoading")
        progressBar.visibility = VISIBLE
        recyclerView.visibility = GONE
        errorMessage.visibility = GONE
    }

    private fun showError(message: String) {
        Timber.d("showError -> message: $message")
        progressBar.visibility = GONE
        recyclerView.visibility = GONE
        errorMessage.visibility = VISIBLE
        errorMessage.text = message
    }

    object VModelProvider {

        fun prepareNewsFeedViewModel(owner: ViewModelStoreOwner, context: Context): NewsFeedViewModel {
            val savedARepository =
                SavedAtDataBaseRepository(NewsDataBase.getDatabase().savedAtDao())
            val localRepository = NewsDataBaseRepository(NewsDataBase.getDatabase().newsDao())
            val remoteRepository = NewsServiceRepository(context)

            val combinedNewsDataBaseRepository =
                CombinedNewsDataBaseRepository(savedARepository, localRepository, remoteRepository)
            val transformer = ToNewsModelListTransformer(ToNewsModelTransformer())

            val useCase = GetNews(combinedNewsDataBaseRepository, transformer)

            val viewModelFactory = NewsFeedViewModelFactory(useCase)
            return ViewModelProvider(owner, viewModelFactory)[NewsFeedViewModel::class.java]
        }
    }
}
