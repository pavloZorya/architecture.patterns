package com.arcchitecturepatterns.common.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arcchitecturepatterns.R
import com.arcchitecturepatterns.mvvm.view.data.NewsModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.image_view_holder.view.*

class NewsFeedRecyclerViewAdapter(
    private val newsList: ArrayList<NewsModel>,
    private val listener: ImageClickListener? = null
) : RecyclerView.Adapter<NewsFeedRecyclerViewAdapter.NewsViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as NewsModel
            listener?.onClick(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_view_holder, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val new = newsList[position]
        holder.bind(new)

        with(holder.mView) {
            tag = new
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = newsList.size

    fun addItems(items: List<NewsModel>, position: Int) {
        newsList.addAll(position, items)
        notifyItemRangeInserted(position, items.size)
    }

    inner class NewsViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        private val imageView: ImageView = mView.image
        private val title: TextView = mView.content

        fun bind(news: NewsModel) {
            title.text = news.title
            Glide
                .with(itemView)
                .load(news.imageUrl)
                .centerCrop()
                .into(imageView)
        }
    }

    interface ImageClickListener {
        fun onClick(item: NewsModel)
    }
}
