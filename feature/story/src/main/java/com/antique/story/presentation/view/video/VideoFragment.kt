package com.antique.story.presentation.view.video

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
import com.antique.story.databinding.FragmentVideoBinding
import com.antique.story.di.StoryComponentProvider
import com.antique.story.presentation.view.add.AddStoryViewModel
import com.antique.story.presentation.view.picture.PictureListAdapter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class VideoFragment : Fragment() {
    private var _binding: FragmentVideoBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val addStoryViewModel by navGraphViewModels<AddStoryViewModel>(R.id.add_story_nav_graph) { viewModelFactory }
    private lateinit var videoComplete: MenuItem
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
        (requireActivity().applicationContext as StoryComponentProvider).provide().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
    }

    private fun initialize() {
        setupInsets()
        setupMenu()
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

    private fun setupMenu() {
        binding.videoToolbarView.inflateMenu(R.menu.menu_picture)
        videoComplete = binding.videoToolbarView.menu.findItem(R.id.picture_complete)
        videoComplete.isEnabled = false

        binding.videoToolbarView.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.picture_complete -> {
                    addStoryViewModel.setVideos(videoListAdapter.getSelectedVideos())
                    findNavController().navigateUp()
                    true
                }
                else -> false
            }
        }
    }

    private fun setupRecyclerView() {
        videoListAdapter = VideoListAdapter {
            videoComplete.isEnabled = it > 0
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

        val projection = arrayOf(MediaStore.Video.Media._ID)
        val selection = "${MediaStore.Video.Media.DURATION} <= ?"
        val selectionArgs = arrayOf(TimeUnit.MILLISECONDS.convert(3, TimeUnit.MINUTES).toString())

        val cursor = requireActivity().contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
            MediaStore.Video.VideoColumns.DATE_TAKEN + " DESC"
        )

        val videos = mutableListOf<String>()

        cursor?.let {
            val idColumnIdx = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            while(cursor.moveToNext()) {
                val contentUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, cursor.getLong(idColumnIdx))

                videos.add(contentUri.toString())
            }
            it.close()
        }

        binding.uris = videos
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}