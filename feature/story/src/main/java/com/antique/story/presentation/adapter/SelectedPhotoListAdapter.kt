package com.antique.story.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.antique.story.R
import com.antique.story.databinding.ListItemSelectedPhotoBinding
import com.bumptech.glide.Glide

class SelectedPhotoListAdapter(
    private val onItemClickListener: (String) -> Unit
) : ListAdapter<String, SelectedPhotoListAdapter.SelectedPhotoListViewHolder>(diffUtil) {
    inner class SelectedPhotoListViewHolder(private val binding: ListItemSelectedPhotoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            Glide.with(binding.photoView.context)
                .load(item)
                .into(binding.photoView)

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedPhotoListViewHolder {
        val binding = DataBindingUtil.inflate<ListItemSelectedPhotoBinding>(LayoutInflater.from(parent.context), R.layout.list_item_selected_photo, parent, false)
        return SelectedPhotoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectedPhotoListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}