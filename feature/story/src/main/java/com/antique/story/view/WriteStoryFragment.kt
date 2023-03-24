package com.antique.story.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.antique.common.util.ViewInsetsCallback
import com.antique.story.R
import com.antique.story.adapter.SelectedPhotoListAdapter
import com.antique.story.adapter.SelectedVideoListAdapter
import com.antique.story.databinding.FragmentWriteStoryBinding
import com.antique.story.di.StoryComponentProvider
import com.antique.story.viewmodel.StoryViewModel
import com.antique.story.viewmodel.WriteStoryViewModel
import javax.inject.Inject

class WriteStoryFragment : Fragment() {
    private var _binding: FragmentWriteStoryBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val storyViewModel by navGraphViewModels<WriteStoryViewModel>(R.id.write_story_nav_graph) { viewModelFactory }

    private lateinit var registerStoryMenuItem: MenuItem
    private lateinit var selectedPhotoListAdapter: SelectedPhotoListAdapter
    private lateinit var selectedVideoListAdapter: SelectedVideoListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as StoryComponentProvider).provideStoryComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_write_story, container, false)
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
        setupViewListener()
        setupObservers()
    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root, ViewInsetsCallback(insetTypes = WindowInsetsCompat.Type.systemBars(), insetTypes2 = WindowInsetsCompat.Type.ime()))
    }

    private fun setupToolbar() {
        binding.writeStoryToolbarView.inflateMenu(R.menu.write_story_menu)
        registerStoryMenuItem = binding.writeStoryToolbarView.menu.findItem(R.id.register_story)
        registerStoryMenuItem.isEnabled = false

        binding.writeStoryToolbarView.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.register_story -> {
                    //postViewModel.registerPost()
                    findNavController().navigateUp()
                    true
                }
                else -> false
            }
        }
    }

    private fun setupRecyclerView() {
        selectedPhotoListAdapter = SelectedPhotoListAdapter()
        binding.selectedPhotoListView.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.selectedPhotoListView.adapter = selectedPhotoListAdapter

        selectedVideoListAdapter = SelectedVideoListAdapter()
        binding.selectedVideoListView.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.selectedVideoListView.adapter = selectedVideoListAdapter
    }

    private fun setupViewListener() {
        binding.addPhotoView.setOnClickListener {
            findNavController().navigate(R.id.action_writeStoryFragment_to_photoGalleryFragment)
        }
        binding.addVideoView.setOnClickListener {
            findNavController().navigate(R.id.action_writeStoryFragment_to_videoGalleryFragment)
        }
    }

    private fun setupObservers() {
        storyViewModel.photos.observe(viewLifecycleOwner) {
            selectedPhotoListAdapter.submitList(it)
        }

        storyViewModel.videos.observe(viewLifecycleOwner) {
            selectedVideoListAdapter.submitList(it)
        }
    }
}