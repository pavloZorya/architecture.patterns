package com.arcchitecturepatterns.mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.arcchitecturepatterns.R
import com.arcchitecturepatterns.common.data.image.Image
import com.arcchitecturepatterns.common.view.ImagesRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_images_list.*
import timber.log.Timber

class ImagesFragment : Fragment() {

    private var columnCount = 1
    lateinit var viewModel: ImagesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_images_list, container, false)
        viewModel = ViewModelProviders.of(this).get(ImagesListViewModel::class.java)

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
        viewModel.loadImages()
    }

    private fun initializeViewModelListeners(viewModel: ImagesListViewModel) {
        Timber.d("initializeViewModelListeners")
        viewModel.imagesList.observe(this, Observer {
            Timber.d("images list size: ${it.size}")
            if (it.isNotEmpty()) {
                showImages(it)
            } else {
                showEmptyResult()
            }
        })
        viewModel.isLoading.observe(this, Observer {
            Timber.d("isLoading: $it")
            if (it) {
                showLoading()
            }
        })
        viewModel.error.observe(this, Observer {
            Timber.d("error: $it")
            showError(it)
        })
    }

    private fun showImages(images: List<Image>) {
        Timber.d("showImages: ${images.size}")
        recyclerView.adapter =
            ImagesRecyclerViewAdapter(
                images
            )

        recyclerView.visibility = VISIBLE
        progressBar.visibility = GONE
        errorMessage.visibility = GONE
    }

    private fun showEmptyResult() {
        Timber.d("showEmptyResult")
        progressBar.visibility = GONE
        recyclerView.visibility = GONE
        errorMessage.visibility = VISIBLE
        errorMessage.text = "There are no result"
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

}
