package com.antique.story.presentation.view.add

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.antique.common.util.ApiState
import com.antique.common.util.ViewInsetsCallback
import com.antique.story.R
import com.antique.story.databinding.FragmentAddStoryBinding
import com.antique.story.di.StoryComponentProvider
import com.antique.story.presentation.view.main.StoryViewModel
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class AddStoryFragment : Fragment() {
    private var _binding: FragmentAddStoryBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val storyViewModel by navGraphViewModels<StoryViewModel>(R.id.story_nav_graph) { viewModelFactory }
    private val addStoryViewModel by navGraphViewModels<AddStoryViewModel>(R.id.add_story_nav_graph) { viewModelFactory }

    private lateinit var registerStory: MenuItem
    private lateinit var selectedPictureListAdapter: SelectedPictureListAdapter
    private lateinit var selectedVideoListAdapter: SelectedVideoListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as StoryComponentProvider).provide().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_story, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.vm = addStoryViewModel

        initialize()
    }

    private fun initialize() {
        setupInsets()
        setupMenu()
        setupRecyclerView()
        setupViewListener()
        setupObservers()
    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root, ViewInsetsCallback(insetTypes = WindowInsetsCompat.Type.systemBars(), insetTypes2 = WindowInsetsCompat.Type.ime()))
    }

    private fun setupMenu() {
        binding.addStoryToolbarView.inflateMenu(R.menu.menu_add_story)
        registerStory = binding.addStoryToolbarView.menu.findItem(R.id.register_story)
        registerStory.isEnabled = false

        binding.addStoryToolbarView.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.register_story -> {
                    binding.addStoryProgressView.isVisible = true
                    addStoryViewModel.registerStory(binding.inputBodyView.text.toString())
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun setupRecyclerView() {
        selectedPictureListAdapter = SelectedPictureListAdapter {
            addStoryViewModel.removePicture(it)
        }
        binding.selectedPictureListView.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.selectedPictureListView.adapter = selectedPictureListAdapter

        selectedVideoListAdapter = SelectedVideoListAdapter {
            addStoryViewModel.removeVideo(it)
        }
        binding.selectedVideoListView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.selectedVideoListView.adapter = selectedVideoListAdapter
    }

    private fun setupViewListener() {
        binding.addPictureView.setOnClickListener {
            findNavController().navigate(R.id.action_addStoryFragment_to_pictureFragment)
        }

        binding.addVideoView.setOnClickListener {
            findNavController().navigate(R.id.action_addStoryFragment_to_videoFragment)
        }

        binding.addPlaceView.setOnClickListener {
            findNavController().navigate(R.id.action_addStoryFragment_to_placeFragment)
        }

        binding.removePlaceView.setOnClickListener {
            addStoryViewModel.removePlace()
        }

        binding.inputBodyView.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    registerStory.isEnabled = it.isNotEmpty()
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupObservers() {
        /*
        addStoryViewModel.register.observe(viewLifecycleOwner) {
            when(it) {
                true -> {
                    binding.addStoryProgressView.isVisible = false
                    findNavController().navigateUp()
                }
                false -> {
                    binding.addStoryProgressView.isVisible = false
                    Snackbar.make(binding.root, getString(R.string.register_story_failure_text), Snackbar.LENGTH_SHORT).show()
                }
            }
        }

         */

        addStoryViewModel.register.observe(viewLifecycleOwner) {
            when(it) {
                is ApiState.Success -> {
                    binding.addStoryProgressView.isVisible = false
                    storyViewModel.addNewStory(it.items)
                    findNavController().navigateUp()
                }
                is ApiState.Error -> {
                    binding.addStoryProgressView.isVisible = false
                    Snackbar.make(binding.root, getString(R.string.register_story_failure_text), Snackbar.LENGTH_SHORT).show()
                }
                is ApiState.Loading -> {
                    binding.addStoryProgressView.isVisible = true
                }
            }
        }
    }
}