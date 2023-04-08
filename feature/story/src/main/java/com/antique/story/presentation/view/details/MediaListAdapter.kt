package com.antique.story.presentation.view.details

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.antique.common.util.Constant
import com.antique.story.R
import com.antique.story.databinding.ListItemMediaBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource

class MediaListAdapter : ListAdapter<String, MediaListAdapter.MediaListViewHolder>(diffUtil) {
    private val players = HashMap<Int, ExoPlayer>()

    inner class MediaListViewHolder(private val binding: ListItemMediaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            if(isPhoto(item)) {
                binding.imageViewer.isVisible = true
                binding.videoPlayer.isVisible = false
                binding.photoUrl = item
            } else {
                binding.videoPlayer.isVisible = true
                binding.imageViewer.isVisible = false

                val player = ExoPlayer.Builder(binding.root.context).build()
                players[absoluteAdapterPosition] = player

                binding.videoPlayer.player = player
                player.seekTo(0)
                player.repeatMode = Player.REPEAT_MODE_ONE

                val dataSourceFactory = DefaultDataSource.Factory(binding.root.context)
                val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(item))

                player.setMediaSource(mediaSource)
                player.prepare()

                if(absoluteAdapterPosition == 0) {
                    player.playWhenReady = true
                    player.play()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaListViewHolder {
        val binding = DataBindingUtil.inflate<ListItemMediaBinding>(LayoutInflater.from(parent.context), R.layout.list_item_media, parent, false)
        return MediaListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MediaListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun isVideo(item: String) = item.contains(Constant.VIDEO)
    private fun isPhoto(item: String) = item.contains(Constant.PHOTO)

    fun isVideo(position: Int): Boolean {
        return if(itemCount > 0) {
            isVideo(getItem(position))
        } else {
            false
        }
    }

    fun play(position: Int) {
        players[position]?.let {
            it.playWhenReady = true
            it.play()
        }
    }

    fun pause(position: Int) {
        players[position]?.let {
            it.playWhenReady = false
            it.pause()
        }
    }

    fun resume(position: Int) {
        players[position]?.let {
            it.playWhenReady = true
            it.play()
        }
    }

    fun clearAll() {
        players.forEach {
            it.value.playWhenReady = false
            it.value.pause()
            it.value.seekTo(0)
        }
    }

    fun release() {
        players.forEach {
            it.value.release()
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
}