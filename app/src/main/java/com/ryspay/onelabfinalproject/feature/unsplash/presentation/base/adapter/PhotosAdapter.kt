package com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ryspay.onelabfinalproject.R
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.entity.PhotoItemUI
import kotlinx.android.synthetic.main.item_photo.view.*
import java.lang.NullPointerException

class PhotosAdapter(
    private val clickListener: PhotoClickListener
): PagedListAdapter<PhotoItemUI, PhotosAdapter.PhotoViewHolder>(diffUtil) {
    class PhotoViewHolder(view: View): RecyclerView.ViewHolder(view)

    companion object{
        val diffUtil = object: DiffUtil.ItemCallback<PhotoItemUI>(){
            override fun areItemsTheSame(oldItem: PhotoItemUI, newItem: PhotoItemUI): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PhotoItemUI, newItem: PhotoItemUI): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = getItem(position)
        if (item == null){
            throw NullPointerException()
        }

//        Glide.with(holder.itemView.context)
//            .load(item.raw_url)
//            .diskCacheStrategy(DiskCacheStrategy.ALL)
//            .centerCrop()
//            .fitCenter()
//            .error(R.drawable.image_placeholder)
//            .into(holder.itemView.photoImageView)

        holder.itemView.test_text.text = item.raw_url

        holder.itemView.setOnClickListener { clickListener.onPhotoCLick(item.id) }
    }
}

interface PhotoClickListener {
    fun onPhotoCLick(id: String)
}

