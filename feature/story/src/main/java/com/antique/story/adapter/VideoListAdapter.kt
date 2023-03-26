package com.antique.story.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.antique.story.R
import com.antique.story.data.story.story.Video
import com.antique.story.databinding.ListItemVideoBinding
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat

class VideoListAdapter(
    private val onItemClickListener: (Int) -> Unit
) : ListAdapter<Video, VideoListAdapter.VideoListViewHolder>(diffUtil) {
    private val selectedVideos = mutableListOf<Video>()

    inner class VideoListViewHolder(private val binding: ListItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Video) {
            val second = (item.duration / 1000)
            val decimalFormat = DecimalFormat("00")
            binding.videoDurationView.text = "${decimalFormat.format(second / 60)} : ${decimalFormat.format(second % 60)}"

            Glide.with(binding.galleryVideoView.context)
                .load(item.uri)
                .into(binding.galleryVideoView)

            if(selectedVideos.contains(item)) {
                binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.orange))
            } else {
                binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.white))
            }

            binding.root.setOnClickListener {
                if(selectedVideos.contains(item)) {
                    selectedVideos.remove(item)
                    binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.white))
                    onItemClickListener(selectedVideos.size)
                } else {
                    if(selectedVideos.size < 3) {
                        binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.orange))
                        selectedVideos.add(item)
                        onItemClickListener(selectedVideos.size)
                    } else {
                        Snackbar.make(binding.root, "3개까지 선택할 수 있습니다", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Video>() {
            override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoListViewHolder {
        val binding = DataBindingUtil.inflate<ListItemVideoBinding>(LayoutInflater.from(parent.context), R.layout.list_item_video, parent, false)
        return VideoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getSelectedVideos() = selectedVideos
}