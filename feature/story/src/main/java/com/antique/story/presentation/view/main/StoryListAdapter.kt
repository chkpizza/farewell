package com.antique.story.presentation.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.antique.story.R
import com.antique.story.databinding.ListItemStoryBinding
import com.antique.story.domain.model.Story

class StoryListAdapter(
    private val onItemClickListener: (Story) -> Unit
) : ListAdapter<Story, StoryListAdapter.StoryListViewHolder>(diffUtil) {
    inner class StoryListViewHolder(private val binding: ListItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Story) {
            binding.root.setOnClickListener {
                onItemClickListener(item)
            }
            if(item.pictures.isNotEmpty()) {
                binding.url = item.pictures[0]
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryListViewHolder {
        val binding = DataBindingUtil.inflate<ListItemStoryBinding>(LayoutInflater.from(parent.context), R.layout.list_item_story, parent, false)
        return StoryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE
    }

    companion object {
        const val VIEW_TYPE = 1001

        val diffUtil = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }
        }
    }

}