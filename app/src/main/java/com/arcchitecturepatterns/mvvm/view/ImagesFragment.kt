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

        with(recyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
        }
        viewModel.loadImages()
    }

    private fun initializeViewModelListeners(viewModel: ImagesListViewModel) {
        viewModel.imagesList.observe(this, Observer {
            if (it.isNotEmpty()) {
                showImages(it)
            } else {
                showEmptyResult()
            }
        })
        viewModel.isLoading.observe(this, Observer {
            if (it) {
                showLoading()
            }
        })
        viewModel.error.observe(this, Observer {
            showError(it)
        })
    }

    private fun showImages(images: List<Image>) {
        recyclerView.adapter =
            ImagesRecyclerViewAdapter(
                images
            )

        recyclerView.visibility = VISIBLE
        progressBar.visibility = GONE
        errorMessage.visibility = GONE
    }

    private fun showEmptyResult() {
        progressBar.visibility = GONE
        recyclerView.visibility = GONE
        errorMessage.visibility = VISIBLE
        errorMessage.text = "There are no result"
    }

    private fun showLoading() {
        progressBar.visibility = VISIBLE
        recyclerView.visibility = GONE
        errorMessage.visibility = GONE
    }

    private fun showError(message: String) {
        progressBar.visibility = GONE
        recyclerView.visibility = GONE
        errorMessage.visibility = VISIBLE
        errorMessage.text = message
    }

}
