package com.antique.story.presentation.view.picture

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
import com.antique.story.databinding.FragmentPictureBinding
import com.antique.story.di.StoryComponentProvider
import com.antique.story.presentation.view.add.AddStoryViewModel
import javax.inject.Inject

class PictureFragment : Fragment() {
    private var _binding: FragmentPictureBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val addStoryViewModel by navGraphViewModels<AddStoryViewModel>(R.id.add_story_nav_graph) { viewModelFactory }

    private lateinit var pictureComplete: MenuItem
    private lateinit var pictureListAdapter: PictureListAdapter

    private val storagePermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if(isGranted) {
            loadImages()
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
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_picture, container, false)
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
        binding.selectImageToolbarView.inflateMenu(R.menu.menu_picture)
        pictureComplete = binding.selectImageToolbarView.menu.findItem(R.id.picture_complete)
        pictureComplete.isEnabled = false

        binding.selectImageToolbarView.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.picture_complete -> {
                    addStoryViewModel.setPictures(pictureListAdapter.getSelectedPictures())
                    findNavController().navigateUp()
                    true
                }
                else -> false
            }
        }
    }

    private fun setupRecyclerView() {
        pictureListAdapter = PictureListAdapter {
            pictureComplete.isEnabled = it > 0
        }
        binding.pictureListView.layoutManager = GridLayoutManager(requireActivity(), 3)
        binding.pictureListView.adapter = pictureListAdapter
    }

    private fun setupPermissions() {
        if(ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            loadImages()
        } else {
            storagePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadImages() {
        val collection = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val projection = arrayOf(MediaStore.Images.Media._ID)
        val cursor = requireActivity().contentResolver.query(
            collection,
            projection,
            null,
            null,
            MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC"
        )

        val pictures = mutableListOf<String>()

        cursor?.let {
            val columnIdx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while(cursor.moveToNext()) {
                val uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursor.getLong(columnIdx))
                pictures.add(uri.toString())
            }
            it.close()
        }
        binding.uris = pictures
    }
}