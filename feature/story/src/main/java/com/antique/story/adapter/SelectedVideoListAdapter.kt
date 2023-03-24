package com.antique.story.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.antique.story.R
import com.antique.story.data.Video
import com.antique.story.databinding.ListItemSelectedVideoBinding
import com.bumptech.glide.Glide
import java.text.DecimalFormat

class SelectedVideoListAdapter(
    private val onItemClickListener: (Video) -> Unit
) : ListAdapter<Video, SelectedVideoListAdapter.SelectedVideoListViewHolder>(diffUtil) {
    inner class SelectedVideoListViewHolder(private val binding: ListItemSelectedVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Video) {
            val second = (item.duration / 1000)
            val decimalFormat = DecimalFormat("00")
            binding.videoDurationView.text = "${decimalFormat.format(second / 60)} : ${decimalFormat.format(second % 60)}"

            Glide.with(binding.videoView.context)
                .load(item.uri)
                .into(binding.videoView)

            binding.removeVideoView.setOnClickListener {
                onItemClickListener(item)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedVideoListViewHolder {
        val binding = DataBindingUtil.inflate<ListItemSelectedVideoBinding>(LayoutInflater.from(parent.context), R.layout.list_item_selected_video, parent, false)
        return SelectedVideoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectedVideoListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}