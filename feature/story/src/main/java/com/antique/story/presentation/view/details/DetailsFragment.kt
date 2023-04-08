package com.antique.story.presentation.view.details

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import androidx.viewpager2.widget.ViewPager2
import com.antique.common.util.ApiState
import com.antique.story.R
import com.antique.story.databinding.FragmentDetailsBinding
import com.antique.story.di.StoryComponentProvider
import com.antique.story.presentation.view.main.StoryViewModel
import javax.inject.Inject

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val detailsViewModel by lazy { ViewModelProvider(this, viewModelFactory).get(DetailsViewModel::class.java) }
    private val storyViewModel by navGraphViewModels<StoryViewModel>(R.id.story_nav_graph) { viewModelFactory }

    private val args: DetailsFragmentArgs by navArgs()
    private val id by lazy { args.id }

    private lateinit var mediaListAdapter: MediaListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as StoryComponentProvider).provide().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.vm = detailsViewModel

        initialize()
    }

    private fun initialize() {
        setupInsets()
        setupViewPager()
        setupViewState()
    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            view.updatePadding(
                top = insets.systemWindowInsets.top
            )
            insets
        }
    }

    private fun setupViewPager() {
        mediaListAdapter = MediaListAdapter()
        binding.mediaListView.adapter = mediaListAdapter
        binding.mediaListIndicatorView.attachTo(binding.mediaListView)

        binding.mediaListView.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                mediaListAdapter.clearAll()
                mediaListAdapter.play(position)
            }
        })
    }

    private fun setupViewState() {
        detailsViewModel.fetchStory(id)
    }

    override fun onResume() {
        super.onResume()
        if(mediaListAdapter.isVideo(binding.mediaListView.currentItem)) {
            mediaListAdapter.resume(binding.mediaListView.currentItem)
        }
    }

    override fun onPause() {
        super.onPause()
        if(mediaListAdapter.isVideo(binding.mediaListView.currentItem)) {
            mediaListAdapter.pause(binding.mediaListView.currentItem)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaListAdapter.release()
        _binding = null
    }
}