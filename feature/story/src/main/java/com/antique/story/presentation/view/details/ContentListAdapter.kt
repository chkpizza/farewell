package com.antique.story.presentation.view.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.antique.common.util.Constant
import com.antique.story.R
import com.antique.story.domain.model.Content
import com.antique.story.databinding.ListItemContentBinding
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource

class ContentListAdapter : ListAdapter<Content, ContentListAdapter.ContentListViewHolder>(diffUtil) {
    val players = HashMap<Int, ExoPlayer>()

    inner class ContentListViewHolder(private val binding: ListItemContentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Content) {
            if(item.type == Constant.VIDEO_CONTENT) {
                binding.videoPlayer.isVisible = true
                binding.imageViewer.isVisible = false

                val player = ExoPlayer.Builder(binding.root.context).build()
                players[absoluteAdapterPosition] = player

                player.addListener(object : Player.Listener {
                    override fun onPlayerError(error: PlaybackException) {
                        super.onPlayerError(error)
                    }

                    override fun onPlaybackStateChanged(playbackState: Int) {
                        super.onPlaybackStateChanged(playbackState)
                    }
                })

                binding.videoPlayer.player = player
                player.seekTo(0)
                player.repeatMode = Player.REPEAT_MODE_ONE

                val dataSourceFactory = DefaultDataSource.Factory(binding.root.context)
                val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(item.uri))
                player.setMediaSource(mediaSource)
                player.prepare()

                if(absoluteAdapterPosition == 0) {
                    player.playWhenReady = true
                    player.play()
                }
            } else {
                binding.videoPlayer.isVisible = false
                binding.imageViewer.isVisible = true
                Glide.with(binding.imageViewer.context)
                    .load(item.uri)
                    .into(binding.imageViewer)
            }
        }
    }
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Content>() {
            override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentListViewHolder {
        val binding = DataBindingUtil.inflate<ListItemContentBinding>(LayoutInflater.from(parent.context), R.layout.list_item_content, parent, false)
        return ContentListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContentListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun isVideo(position: Int): Boolean {
        return if(itemCount > 0) {
            getItem(position).type == Constant.VIDEO_CONTENT
        } else {
            false
        }
    }

    fun pause(position: Int) {
        players[position]?.let {
            it.pause()
            it.playWhenReady = false
        }
    }

    fun resume(position: Int) {
        players[position]?.let {
            it.playWhenReady = true
            it.play()
        }
    }

    fun release() {
        players.forEach {
            it.value.release()
        }
    }
}