package com.arcchitecturepatterns.mvp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.arcchitecturepatterns.R
import com.arcchitecturepatterns.common.data.image.Image
import com.arcchitecturepatterns.common.view.ImagesRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_images_list.*

class ImagesFragment : Fragment(), ImagesListContract.ImagesListView {

    private var columnCount = 1
    private val presenter = createPresenter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_images_list, container, false)
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
        presenter.loadImages()
    }

    override fun showImages(images: List<Image>) {
        recyclerView.adapter =
            ImagesRecyclerViewAdapter(
                images
            )

        recyclerView.visibility = VISIBLE
        progressBar.visibility = GONE
        errorMessage.visibility = GONE
    }

    override fun showEmptyResult() {
        progressBar.visibility = GONE
        recyclerView.visibility = GONE
        errorMessage.visibility = VISIBLE
        errorMessage.text = "There are no result"
    }

    override fun showLoading() {
        progressBar.visibility = VISIBLE
        recyclerView.visibility = GONE
        errorMessage.visibility = GONE
    }

    override fun showError(message: String) {
        progressBar.visibility = GONE
        recyclerView.visibility = GONE
        errorMessage.visibility = VISIBLE
        errorMessage.text = message
    }

    private fun createPresenter(): ImagesListContract.ImagesListPresenter =
        ImagesListPresenter(this)
}
