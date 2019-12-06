package com.arcchitecturepatterns.common.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arcchitecturepatterns.R
import com.arcchitecturepatterns.common.data.image.Image
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.image_view_holder.view.*

class ImagesRecyclerViewAdapter(
    private val imagesList: List<Image>,
    private val listener: ImageClickListener? = null
) : RecyclerView.Adapter<ImagesRecyclerViewAdapter.ImageViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Image
            listener?.onClick(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_view_holder, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = imagesList[position]
        holder.bind(image)

        with(holder.mView) {
            tag = image
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = imagesList.size

    inner class ImageViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        private val imageView: ImageView = mView.image
        private val title: TextView = mView.content

        fun bind(image: Image) {
            title.text = image.title
            Glide
                .with(itemView)
                .load(image.link)
                .centerCrop()
                .into(imageView)
        }
    }

    interface ImageClickListener {
        fun onClick(item: Image)
    }
}
