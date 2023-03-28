package com.antique.story.presentation.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import androidx.viewpager2.widget.ViewPager2
import com.antique.common.util.ApiState
import com.antique.common.util.EventObserver
import com.antique.story.R
import com.antique.story.presentation.adapter.ContentListAdapter
import com.antique.story.databinding.FragmentStoryDetailsBinding
import com.antique.story.di.StoryComponentProvider
import com.antique.story.presentation.viewmodel.StoryDetailsViewModel
import com.antique.story.presentation.viewmodel.StoryViewModel
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class StoryDetailsFragment : Fragment() {
    private var _binding: FragmentStoryDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: StoryDetailsFragmentArgs by navArgs()
    private val storyId by lazy { args.storyId }

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val storyDetailsViewModel by lazy { ViewModelProvider(this, viewModelFactory)[StoryDetailsViewModel::class.java] }
    private val storyViewModel by navGraphViewModels<StoryViewModel>(R.id.story_nav_graph) { viewModelFactory }

    private lateinit var contentListAdapter: ContentListAdapter
    private lateinit var removeStoryMenuItem: MenuItem

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as StoryComponentProvider).provideStoryComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_story_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        setupInsets()
        setupDataBinding()
        setupToolbar()
        setupRecyclerView()
        setupViewState()
        setupObservers()
    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            view.updatePadding(
                top = insets.systemWindowInsets.top,
                bottom = insets.systemWindowInsets.bottom
            )
            insets
        }
    }

    private fun setupDataBinding() {
        binding.lifecycleOwner = this
    }

    private fun setupToolbar() {
        binding.storyDetailsToolbarView.inflateMenu(R.menu.story_details_menu)
        removeStoryMenuItem = binding.storyDetailsToolbarView.menu.findItem(R.id.remove_story)

        binding.storyDetailsToolbarView.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.remove_story -> {
                    storyDetailsViewModel.removeStory(storyId)
                    true
                }
                else -> false
            }
        }
    }

    private fun setupRecyclerView() {
        contentListAdapter = ContentListAdapter()
        binding.contentListView.adapter = contentListAdapter

        binding.contentListView.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                contentListAdapter.players.forEach {
                    if(it.value.isPlaying) {
                        it.value.playWhenReady = false
                        it.value.pause()
                        it.value.seekTo(0)
                    }
                }

                if(contentListAdapter.isVideo(position)) {
                    contentListAdapter.players[position]?.let {
                        it.playWhenReady = true
                        it.play()
                    }
                }
            }
        })
    }

    private fun setupViewState() {
        storyDetailsViewModel.loadStory(storyId)
    }

    private fun setupObservers() {
        storyDetailsViewModel.story.observe(viewLifecycleOwner) {
            when(it) {
                is ApiState.Success -> {
                    binding.storyDetailsProgressView.isVisible = false
                    contentListAdapter.submitList(it.items.contents)

                    binding.bodyView.text = it.items.body

                    if(it.items.place.placeAddress.isNotEmpty()) {
                        binding.locationView.isVisible = true
                        binding.locationNameView.text = it.items.place.placeName
                    } else {
                        binding.locationView.isVisible = false
                    }

                    binding.dateView.text = it.items.date
                }
                is ApiState.Error -> {
                    binding.storyDetailsProgressView.isVisible = false
                    Snackbar.make(binding.root, getString(R.string.load_story_details_failure_text), Snackbar.LENGTH_LONG).show()
                }
                is ApiState.Loading -> {
                    binding.storyDetailsProgressView.isVisible = true
                }
            }
        }

        storyDetailsViewModel.removeStoryState.observe(viewLifecycleOwner, EventObserver {
            when(it) {
                true -> {
                    storyViewModel.removeStory(storyId)
                    findNavController().navigateUp()
                }
                false -> {
                    Snackbar.make(binding.root, getString(R.string.remove_story_failure_text), Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        if(contentListAdapter.isVideo(binding.contentListView.currentItem)) {
            contentListAdapter.pause(binding.contentListView.currentItem)
        }
    }

    override fun onResume() {
        super.onResume()
        if(contentListAdapter.isVideo(binding.contentListView.currentItem)) {
            contentListAdapter.resume(binding.contentListView.currentItem)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        contentListAdapter.release()
        _binding = null
    }
}