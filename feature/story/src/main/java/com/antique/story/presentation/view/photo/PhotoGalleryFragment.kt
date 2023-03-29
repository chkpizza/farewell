package com.antique.story.presentation.view.photo

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
import com.antique.story.databinding.FragmentPhotoGalleryBinding
import com.antique.story.di.StoryComponentProvider
import com.antique.story.presentation.view.write.WriteStoryViewModel
import javax.inject.Inject

class PhotoGalleryFragment : Fragment() {
    private var _binding: FragmentPhotoGalleryBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val storyViewModel by navGraphViewModels<WriteStoryViewModel>(R.id.write_story_nav_graph) { viewModelFactory }

    private lateinit var selectPhotoMenuItem: MenuItem
    private lateinit var photoListAdapter: PhotoListAdapter

    private val storagePermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if(isGranted) {
            loadImages()
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
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo_gallery, container, false)
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
        binding.photoGalleryToolbarView.inflateMenu(R.menu.photo_gallery_menu)
        selectPhotoMenuItem = binding.photoGalleryToolbarView.menu.findItem(R.id.select_image)
        selectPhotoMenuItem.isEnabled = false

        binding.photoGalleryToolbarView.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.select_image -> {
                    storyViewModel.bindPhotos(photoListAdapter.getSelectedPhotos().toList())
                    findNavController().navigateUp()
                    true
                }
                else -> false
            }
        }
    }

    private fun setupRecyclerView() {
        photoListAdapter = PhotoListAdapter {
            selectPhotoMenuItem.isEnabled = it > 0
        }
        binding.photoListView.layoutManager = GridLayoutManager(requireActivity(), 3)
        binding.photoListView.adapter = photoListAdapter
    }

    private fun setupPermissions() {
        if(ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            loadImages()
        } else {
            storagePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
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

        val uris = mutableListOf<String>()

        cursor?.let {
            val columnIdx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while(cursor.moveToNext()) {
                val uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursor.getLong(columnIdx))
                uris.add(uri.toString())
            }
            it.close()
        }

        photoListAdapter.submitList(uris)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}