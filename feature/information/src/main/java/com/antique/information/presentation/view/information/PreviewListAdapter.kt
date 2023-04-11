package com.antique.information.presentation.view.information

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.antique.information.R
import com.antique.information.databinding.ListItemInformationBinding
import com.antique.information.domain.model.Preview
import com.bumptech.glide.Glide

class PreviewListAdapter : ListAdapter<Preview, PreviewListAdapter.PreviewListViewHolder>(diffUtil) {
    inner class PreviewListViewHolder(private val binding: ListItemInformationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Preview) {
            Glide.with(binding.imageView.context)
                .load(item.imageUrl)
                .into(binding.imageView)

            binding.nameView.text = item.name
            binding.addressView.text = item.address
            binding.periodView.text = item.period

            if(item.isFree) {
                binding.amountView.background = ResourcesCompat.getDrawable(binding.root.resources, com.antique.common.R.drawable.shape_rectangle_8dp_pink, null)
                binding.amountView.setTextColor(binding.root.resources.getColor(com.antique.common.R.color.white, null))
                binding.amountView.text = "무료"
            } else {
                binding.amountView.background = ResourcesCompat.getDrawable(binding.root.resources, com.antique.common.R.drawable.shape_rectangle_8dp_orange, null)
                binding.amountView.setTextColor(binding.root.resources.getColor(com.antique.common.R.color.white, null))
                binding.amountView.text = "유료"
            }
        }
    }
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Preview>() {
            override fun areItemsTheSame(oldItem: Preview, newItem: Preview): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Preview, newItem: Preview): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewListViewHolder {
        val binding = DataBindingUtil.inflate<ListItemInformationBinding>(LayoutInflater.from(parent.context), R.layout.list_item_information, parent, false)
        return PreviewListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PreviewListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}