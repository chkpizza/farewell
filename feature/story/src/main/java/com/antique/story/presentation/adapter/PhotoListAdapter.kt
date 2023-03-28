package com.antique.story.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.antique.story.R
import com.antique.story.databinding.ListItemPhotoBinding
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class PhotoListAdapter(
    private val onItemClickListener: (Int) -> Unit
) : ListAdapter<String, PhotoListAdapter.PhotoListViewHolder>(diffUtil) {
    private val selectedImages = mutableListOf<String>()

    inner class PhotoListViewHolder(private val binding: ListItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            Glide.with(binding.galleryImageView.context)
                .load(item)
                .into(binding.galleryImageView)

            if(selectedImages.contains(item)) {
                binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, com.antique.common.R.color.orange))
            } else {
                binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, com.antique.common.R.color.white))
            }

            binding.root.setOnClickListener {
                if(selectedImages.contains(item)) {
                    selectedImages.remove(item)
                    binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, com.antique.common.R.color.white))
                    onItemClickListener(selectedImages.size)
                } else {
                    if(selectedImages.size < 3) {
                        binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, com.antique.common.R.color.orange))
                        selectedImages.add(item)
                        onItemClickListener(selectedImages.size)
                    } else {
                        Snackbar.make(binding.root, "3개까지 선택할 수 있습니다", Snackbar.LENGTH_SHORT).show()
                    }
                }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListViewHolder {
        val binding = DataBindingUtil.inflate<ListItemPhotoBinding>(LayoutInflater.from(parent.context), R.layout.list_item_photo, parent, false)
        return PhotoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getSelectedPhotos() = selectedImages
}