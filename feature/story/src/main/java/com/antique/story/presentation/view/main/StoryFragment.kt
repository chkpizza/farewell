package com.antique.story.presentation.view.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.antique.common.util.ApiState
import com.antique.common.util.ViewInsetsCallback
import com.antique.story.R
import com.antique.story.databinding.FragmentStoryBinding
import com.antique.story.di.StoryComponentProvider
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class StoryFragment : Fragment() {
    private var _binding: FragmentStoryBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val storyViewModel by navGraphViewModels<StoryViewModel>(R.id.story_nav_graph) { viewModelFactory }

    private lateinit var doorAdapter: DoorAdapter
    private lateinit var storyListAdapter: StoryListAdapter
    private lateinit var concatAdapter: ConcatAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as StoryComponentProvider).provide().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_story, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.vm = storyViewModel

        initialize()
    }

    private fun initialize() {
        setupInsets()
        setupRecyclerView()
        setupViewListener()
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

    private fun setupRecyclerView() {
        doorAdapter = DoorAdapter()
        storyListAdapter = StoryListAdapter {

        }

        val config = ConcatAdapter.Config.Builder().apply {
            setIsolateViewTypes(false)
        }.build()

        concatAdapter = ConcatAdapter(config, doorAdapter, storyListAdapter)

        val layoutManager = GridLayoutManager(requireActivity(), 12)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = when(concatAdapter.getItemViewType(position)) {
                DoorAdapter.VIEW_TYPE -> 12
                StoryListAdapter.VIEW_TYPE -> 4
                else -> 12
            }
        }

        binding.storyListView.layoutManager = layoutManager
        binding.storyListView.adapter = concatAdapter
    }

    private fun setupViewListener() {
        binding.addStoryView.setOnClickListener {
            findNavController().navigate(R.id.action_storyFragment_to_add_story_nav_graph)
        }
    }

    private fun setupViewState() {
        storyViewModel.fetchDoor()
        storyViewModel.fetchStories()
    }
}