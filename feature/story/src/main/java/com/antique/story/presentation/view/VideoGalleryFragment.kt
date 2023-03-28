package com.antique.story.presentation.view

import android.Manifest
import android.content.ContentUris
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.antique.story.R
import com.antique.story.presentation.adapter.VideoListAdapter
import com.antique.story.data.model.story.story.Video
import com.antique.story.databinding.FragmentVideoGalleryBinding
import com.antique.story.di.StoryComponentProvider
import com.antique.story.presentation.viewmodel.WriteStoryViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class VideoGalleryFragment : Fragment() {
    private var _binding: FragmentVideoGalleryBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val storyViewModel by navGraphViewModels<WriteStoryViewModel>(R.id.write_story_nav_graph) { viewModelFactory }

    private lateinit var selectVideoMenuItem: MenuItem
    private lateinit var videoListAdapter: VideoListAdapter

    private val storagePermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if(isGranted) {
            loadVideos()
        } else {
            findNavController().navigateUp()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as StoryComponentProvider).provideStoryComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_gallery, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        setupInsets()
        setupToolbar()
        setupRecyclerView()
        setupPermissions()
    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            view.updatePadding(
                top = insets.systemWindowInsets.top
            )
            insets
        }
    }

    private fun setupToolbar() {
        binding.videoGalleryToolbarView.inflateMenu(R.menu.video_gallery_menu)
        selectVideoMenuItem = binding.videoGalleryToolbarView.menu.findItem(R.id.select_video)
        selectVideoMenuItem.isEnabled = false

        binding.videoGalleryToolbarView.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.select_video -> {
                    storyViewModel.bindVideos(videoListAdapter.getSelectedVideos().toList())
                    findNavController().navigateUp()
                    true
                }
                else -> false
            }
        }
    }

    private fun setupRecyclerView() {
        videoListAdapter = VideoListAdapter {
            selectVideoMenuItem.isEnabled = it > 0
        }
        binding.videoListView.layoutManager = GridLayoutManager(requireActivity(), 3)
        binding.videoListView.adapter = videoListAdapter
    }

    private fun setupPermissions() {
        if(ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            loadVideos()
        } else {
            storagePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun loadVideos() {
        val collection = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        }

        val projection = arrayOf(MediaStore.Video.Media._ID, MediaStore.Video.Media.DURATION)
        val selection = "${MediaStore.Video.Media.DURATION} <= ?"
        val selectionArgs = arrayOf(TimeUnit.MILLISECONDS.convert(3, TimeUnit.MINUTES).toString())

        val cursor = requireActivity().contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
            MediaStore.Video.VideoColumns.DATE_TAKEN + " DESC"
        )

        val videos = mutableListOf<Video>()

        cursor?.let {
            val idColumnIdx = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val durationColumnIdx = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            while(cursor.moveToNext()) {
                val duration = cursor.getInt(durationColumnIdx)
                val contentUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, cursor.getLong(idColumnIdx))

                videos.add(Video(contentUri.toString(), duration))
            }
            it.close()
        }

        videoListAdapter.submitList(videos)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}