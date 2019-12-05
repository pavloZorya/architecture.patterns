package com.arcchitecturepatterns.mvi.image.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.arcchitecturepatterns.R
import com.arcchitecturepatterns.mvi.data.image.Image
import com.arcchitecturepatterns.mvi.usecase.image.state.ImagesListViewState
import com.hannesdorfmann.mosby3.mvi.MviFragment
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_images_list.*

class ImagesFragment : MviFragment<ImagesListView, ImagesPresenter>(), ImagesListView {

    private var columnCount = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_images_list, container, false)
        this.loadListIntent()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(recyclerView) {
            //            recyclerView.addItemDecoration(DividerItemDecoration(context,
//                DividerItemDecoration.HORIZONTAL))
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
        }
    }

    override fun loadListIntent(): Observable<Boolean> {
        Log.d("ImagesFragment", "loadListIntent")
        return Observable.just(true)
    }

    override fun render(viewState: ImagesListViewState) {
        Log.d("ImagesFragment", "render $viewState")

        when (viewState) {
            is ImagesListViewState.ImagesLoadingNotStartedYet -> {

            }
            is ImagesListViewState.Loading -> {
                renderLoading()
            }
            is ImagesListViewState.ImagesLoadedViewState -> {
                renderImagesList(viewState.images)
            }
            is ImagesListViewState.Error -> {
                renderError()
            }
            is ImagesListViewState.EmptyResult -> {
                renderEmptyResult()
            }
        }
    }

    private fun renderImagesList(images: List<Image>) {
        recyclerView.adapter = ImagesRecyclerViewAdapter(
            images,
            object : ImagesRecyclerViewAdapter.ImageClickListener {
                override fun onClick(item: Image) {

                }

            })

        recyclerView.visibility = VISIBLE
        progressBar.visibility = GONE
        errorMessage.visibility = GONE
    }


    private fun renderError() {
        progressBar.visibility = GONE
        recyclerView.visibility = GONE
        errorMessage.visibility = VISIBLE
        errorMessage.text = "Error happens"
    }

    private fun renderEmptyResult() {
        progressBar.visibility = GONE
        recyclerView.visibility = GONE
        errorMessage.visibility = VISIBLE
        errorMessage.text = "There are no result"
    }

    private fun renderLoading() {
        progressBar.visibility = VISIBLE
        recyclerView.visibility = GONE
        errorMessage.visibility = GONE
    }

    override fun createPresenter(): ImagesPresenter = ImagesPresenter(ImagesListInteractor())
}
