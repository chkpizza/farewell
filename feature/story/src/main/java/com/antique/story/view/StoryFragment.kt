package com.antique.story.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.antique.common.util.ApiState
import com.antique.story.R
import com.antique.story.adapter.StoryListAdapter
import com.antique.story.adapter.DoorAdapter
import com.antique.story.databinding.FragmentStoryBinding
import com.antique.story.di.StoryComponentProvider
import com.antique.story.viewmodel.StoryViewModel
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
        (requireActivity().applicationContext as StoryComponentProvider).provideStoryComponent().inject(this)
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
        initialize()
    }

    private fun initialize() {
        setupInsets()
        setupRecyclerView()
        setupViewState()
        setupViewListener()
        setupObservers()
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
            Toast.makeText(requireActivity(), it.toString(), Toast.LENGTH_SHORT).show()
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

    private fun setupViewState() {
        storyViewModel.loadDoor()
        storyViewModel.loadStories()
    }

    private fun setupViewListener() {
        binding.addStoryView.setOnClickListener {
            findNavController().navigate(R.id.action_storyFragment_to_write_story_nav_graph)
        }

        binding.storyListView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = (binding.storyListView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

                if(lastVisibleItemPosition > 0 && !recyclerView.canScrollVertically(1)) {
                    storyViewModel.loadMoreStories()
                }
            }
        })
    }

    private fun setupObservers() {
        storyViewModel.door.observe(viewLifecycleOwner) {
            when(it) {
                is ApiState.Success -> {
                    doorAdapter.submitList(listOf(it.items))
                }
                is ApiState.Error -> {
                    Snackbar.make(binding.root, getString(R.string.load_door_failure_text), Snackbar.LENGTH_LONG).show()
                }
                is ApiState.Loading -> {

                }
            }
        }
        storyViewModel.story.observe(viewLifecycleOwner) {
            when(it) {
                is ApiState.Success -> {
                    storyListAdapter.submitList(it.items)
                }
                is ApiState.Error -> {
                    Snackbar.make(binding.root, getString(R.string.load_story_failure_text), Snackbar.LENGTH_LONG).show()
                }
                is ApiState.Loading -> {

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}