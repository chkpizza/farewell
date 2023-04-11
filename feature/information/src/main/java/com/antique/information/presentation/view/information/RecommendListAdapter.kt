package com.antique.information.presentation.view.information

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.antique.information.R
import com.antique.information.databinding.ListItemRecommendBinding
import com.bumptech.glide.Glide

class RecommendListAdapter : ListAdapter<String, RecommendListAdapter.RecommendListViewHolder>(diffUtil) {
    inner class RecommendListViewHolder(private val binding: ListItemRecommendBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            Glide.with(binding.imageView.context)
                .load(item)
                .into(binding.imageView)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendListViewHolder {
        val binding = DataBindingUtil.inflate<ListItemRecommendBinding>(LayoutInflater.from(parent.context), R.layout.list_item_recommend, parent, false)
        return RecommendListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}