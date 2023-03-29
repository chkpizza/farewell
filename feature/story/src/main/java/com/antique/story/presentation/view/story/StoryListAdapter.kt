package com.antique.story.presentation.view.story

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.antique.story.R
import com.antique.story.domain.model.Story
import com.antique.story.databinding.ListItemStoryBinding

class StoryListAdapter(
    private val onItemClickListener : (Story) -> Unit
) : ListAdapter<Story, StoryListAdapter.StoryListViewHolder>(diffUtil) {
    inner class StoryListViewHolder(private val binding: ListItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Story) {
            if(item.contents.isNotEmpty()) {
                binding.url = item.contents[0].uri
            }

            binding.root.setOnClickListener {
                onItemClickListener(item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE
    }

    companion object {
        const val VIEW_TYPE = 1002

        val diffUtil = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
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