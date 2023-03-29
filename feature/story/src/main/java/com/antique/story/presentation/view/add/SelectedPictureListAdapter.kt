package com.antique.story.presentation.view.add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.antique.story.R
import com.antique.story.databinding.ListItemSelectedPictureBinding

class SelectedPictureListAdapter(
    private val onItemClickListener: (String) -> Unit
) : ListAdapter<String, SelectedPictureListAdapter.SelectedPictureListViewHolder>(diffUtil) {
    inner class SelectedPictureListViewHolder(private val binding: ListItemSelectedPictureBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.url = item

            binding.removePhotoView.setOnClickListener {
                onItemClickListener(item)
            }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedPictureListViewHolder {
        val binding = DataBindingUtil.inflate<ListItemSelectedPictureBinding>(LayoutInflater.from(parent.context), R.layout.list_item_selected_picture, parent, false)
        return SelectedPictureListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectedPictureListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}