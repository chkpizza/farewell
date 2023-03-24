package com.antique.story.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.antique.story.R
import com.antique.story.databinding.ListItemStoryBinding
import com.bumptech.glide.Glide

class StoryListAdapter(
    private val onItemClickListener : (String) -> Unit
) : ListAdapter<String, StoryListAdapter.StoryListViewHolder>(diffUtil) {
    inner class StoryListViewHolder(private val binding: ListItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            Glide.with(binding.storyPreviewView.context)
                .load(item)
                .into(binding.storyPreviewView)

            binding.root.setOnClickListener {
                onItemClickListener(item)
            }
        }
    }

    companion object {
        const val VIEW_TYPE = 1002

        val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryListViewHolder {
        val binding = DataBindingUtil.inflate<ListItemStoryBinding>(LayoutInflater.from(parent.context),
            R.layout.list_item_story, parent, false)
        return StoryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}