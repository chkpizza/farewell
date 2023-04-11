package com.antique.story.presentation.view.video

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.antique.story.R
import com.antique.story.databinding.ListItemGalleryBinding
import com.google.android.material.snackbar.Snackbar

class VideoListAdapter(
    private val onItemClickListener: (Int) -> Unit
) : ListAdapter<String, VideoListAdapter.VideoListViewHolder>(diffUtil) {
    private val selectedVideos = mutableListOf<String>()

    inner class VideoListViewHolder(private val binding: ListItemGalleryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.url = item

            if(selectedVideos.contains(item)) {
                binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, com.antique.common.R.color.orange))
            } else {
                binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, com.antique.common.R.color.white))
            }

            binding.root.setOnClickListener {
                if(selectedVideos.contains(item)) {
                    selectedVideos.remove(item)
                    binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, com.antique.common.R.color.white))
                    onItemClickListener(selectedVideos.size)
                } else {
                    if(selectedVideos.size < 3) {
                        binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, com.antique.common.R.color.orange))
                        selectedVideos.add(item)
                        onItemClickListener(selectedVideos.size)
                    } else {
                        Snackbar.make(binding.root, binding.root.context.getString(R.string.video_limit_warning_text), Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun getSelectedVideos() = selectedVideos

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoListViewHolder {
        val binding = DataBindingUtil.inflate<ListItemGalleryBinding>(LayoutInflater.from(parent.context), R.layout.list_item_gallery, parent, false)
        return VideoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoListViewHolder, position: Int) {
        holder.bind(getItem(position))
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
}