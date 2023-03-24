package com.antique.story.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.antique.story.R
import com.antique.story.databinding.ItemDoorBinding

class DoorAdapter : ListAdapter<String, DoorAdapter.DoorViewHolder>(diffUtil) {
    inner class DoorViewHolder(private val binding: ItemDoorBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {

        }
    }
    companion object {
        const val VIEW_TYPE = 1000

        val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoorViewHolder {
        val binding = DataBindingUtil.inflate<ItemDoorBinding>(LayoutInflater.from(parent.context), R.layout.item_door, parent, false)
        return DoorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE
    }
}