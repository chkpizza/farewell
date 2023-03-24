package com.antique.story.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.antique.story.R
import com.antique.story.databinding.ListItemVideoBinding
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class VideoListAdapter(
    private val onItemClickListener: (Int) -> Unit
) : ListAdapter<String, VideoListAdapter.VideoListViewHolder>(diffUtil) {
    private val selectedVideos = mutableListOf<String>()

    inner class VideoListViewHolder(private val binding: ListItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            Glide.with(binding.galleryVideoView.context)
                .load(item)
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
        val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
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