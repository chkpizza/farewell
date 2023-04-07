package com.antique.story.presentation.view.place

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.antique.story.R
import com.antique.story.databinding.ListItemPlaceBinding
import com.antique.story.domain.model.PlaceInformation

class PlaceListAdapter(
    private val onItemClickListener: (PlaceInformation) -> Unit
) : ListAdapter<PlaceInformation, PlaceListAdapter.PlaceListViewHolder>(diffUtil) {
    inner class PlaceListViewHolder(private val binding: ListItemPlaceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PlaceInformation) {
            binding.placeInformation = item

            binding.root.setOnClickListener {
                onItemClickListener(item)
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PlaceInformation>() {
            override fun areItemsTheSame(oldItem: PlaceInformation, newItem: PlaceInformation): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: PlaceInformation, newItem: PlaceInformation): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceListViewHolder {
        val binding = DataBindingUtil.inflate<ListItemPlaceBinding>(LayoutInflater.from(parent.context), R.layout.list_item_place, parent, false)
        return PlaceListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaceListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}