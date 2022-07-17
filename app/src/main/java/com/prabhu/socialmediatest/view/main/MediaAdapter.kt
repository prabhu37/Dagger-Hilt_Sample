package com.prabhu.socialmediatest.view.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.prabhu.socialmediatest.R
import com.prabhu.socialmediatest.data.MediaData
import com.prabhu.socialmediatest.databinding.AdapterMediaBinding

/**
 * Created by prabhakaranpanjalingam on 09,July,2022
 */


class MediaAdapter() : RecyclerView.Adapter<MediaAdapter.MediaViewHolder>() {


    private val diffCallback: DiffUtil.ItemCallback<MediaData> =
        object : DiffUtil.ItemCallback<MediaData>() {
            override fun areItemsTheSame(
                oldItem: MediaData,
                newItem: MediaData
            ): Boolean {
                return oldItem.id.equals(newItem.id)
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: MediaData,
                newItem: MediaData
            ): Boolean {
                return oldItem.id === newItem.id
            }
        }
    var mediaList: AsyncListDiffer<MediaData> = AsyncListDiffer(this, diffCallback)
    fun submitList(messageList: List<MediaData>) {
        mediaList.submitList(messageList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val adapterMediaBinding = AdapterMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MediaViewHolder(adapterMediaBinding)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        val mediaDetails: MediaData = mediaList.currentList[position]
        holder.bind(mediaDetails)
    }

    override fun getItemCount(): Int {
        return mediaList.currentList.size
    }

    inner class MediaViewHolder(val binding: AdapterMediaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mediaData: MediaData) {
            binding.mediaResponseData = mediaData
            binding.executePendingBindings()

            binding.root.setOnClickListener(View.OnClickListener {


            })
          Glide.with(binding.root.context)
                .load(mediaData.file)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_default_placeholder)
                .into(binding.ivMedia)

        }

    }
}