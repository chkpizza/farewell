package com.antique.story.presentation.view.add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.antique.story.R
import com.antique.story.databinding.ListItemSelectedVideoBinding

class SelectedVideoListAdapter(
    private val onItemClickListener: (String) -> Unit
) : ListAdapter<String, SelectedVideoListAdapter.SelectedVideoListViewHolder>(diffUtil) {
    inner class SelectedVideoListViewHolder(private val binding: ListItemSelectedVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.url = item

            binding.removeVideoView.setOnClickListener {
                onItemClickListener(item)
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