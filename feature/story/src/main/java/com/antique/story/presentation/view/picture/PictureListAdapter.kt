package com.antique.story.presentation.view.picture

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.antique.story.R
import com.antique.story.databinding.ListItemGalleryBinding
import com.google.android.material.snackbar.Snackbar

class PictureListAdapter(
    private val onItemClickListener: (Int) -> Unit
) : ListAdapter<String, PictureListAdapter.PictureListViewHolder>(diffUtil) {
    private val selectedPictures = mutableListOf<String>()

    inner class PictureListViewHolder(private val binding: ListItemGalleryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.url = item

            if(selectedPictures.contains(item)) {
                binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, com.antique.common.R.color.orange))
            } else {
                binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, com.antique.common.R.color.white))
            }

            binding.root.setOnClickListener {
                if(selectedPictures.contains(item)) {
                    selectedPictures.remove(item)
                    binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, com.antique.common.R.color.white))
                    onItemClickListener(selectedPictures.size)
                } else {
                    if(selectedPictures.size < 3) {
                        selectedPictures.add(item)
                        binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, com.antique.common.R.color.orange))
                        onItemClickListener(selectedPictures.size)
                    } else {
                        Snackbar.make(binding.root, binding.root.context.getString(R.string.picture_limit_warning_text), Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureListViewHolder {
        val binding = DataBindingUtil.inflate<ListItemGalleryBinding>(LayoutInflater.from(parent.context), R.layout.list_item_gallery, parent, false)
        return PictureListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PictureListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getSelectedPictures() = selectedPictures

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
}