package com.antique.story.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.antique.story.R
import com.antique.story.data.place.PlaceInformation
import com.antique.story.databinding.ListItemSearchLocationBinding

class SearchLocationListAdapter(
    private val onItemClickListener: (PlaceInformation) -> Unit
) : ListAdapter<PlaceInformation, SearchLocationListAdapter.SearchPlaceListViewHolder>(diffUtil) {
    inner class SearchPlaceListViewHolder(private val binding: ListItemSearchLocationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PlaceInformation) {
            binding.placeNameView.text = item.placeName
            binding.placeAddressNameView.text = item.placeAddress
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchPlaceListViewHolder {
        val binding = DataBindingUtil.inflate<ListItemSearchLocationBinding>(LayoutInflater.from(parent.context), R.layout.list_item_search_location, parent, false)
        return SearchPlaceListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchPlaceListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}