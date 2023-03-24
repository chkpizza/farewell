package com.antique.story.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.antique.story.R
import com.antique.story.databinding.ItemStoryWrapperBinding

class StoryWrapperAdapter(
    private val storyListAdapter: StoryListAdapter
) : RecyclerView.Adapter<StoryWrapperAdapter.StoryWrapperViewHolder>() {
    inner class StoryWrapperViewHolder(private val binding: ItemStoryWrapperBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.storyWrapperView.layoutManager = GridLayoutManager(binding.root.context, 3)
            binding.storyWrapperView.adapter = storyListAdapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryWrapperViewHolder {
        val binding = DataBindingUtil.inflate<ItemStoryWrapperBinding>(LayoutInflater.from(parent.context), R.layout.item_story_wrapper, parent, false)
        return StoryWrapperViewHolder(binding)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: StoryWrapperViewHolder, position: Int) {
        holder.bind()
    }

    companion object {
        const val VIEW_TYPE = 1001
    }
}