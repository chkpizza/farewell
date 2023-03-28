package com.antique.story.presentation.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.antique.common.util.ApiState
import com.antique.common.util.EventObserver
import com.antique.story.BuildConfig
import com.antique.story.R
import com.antique.story.presentation.adapter.SearchLocationListAdapter
import com.antique.story.databinding.FragmentSearchLocationBinding
import com.antique.story.di.StoryComponentProvider
import com.antique.story.presentation.viewmodel.WriteStoryViewModel
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class SearchLocationFragment : Fragment() {
    private var _binding: FragmentSearchLocationBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val storyViewModel by navGraphViewModels<WriteStoryViewModel>(R.id.write_story_nav_graph) { viewModelFactory }
    private lateinit var searchLocationListAdapter: SearchLocationListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as StoryComponentProvider).provideStoryComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_location, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        setupInsets()
        setupRecyclerView()
        setupViewListener()
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

    private fun setupRecyclerView() {
        searchLocationListAdapter = SearchLocationListAdapter {
            storyViewModel.bindPlace(it)
            storyViewModel.clear()
            findNavController().navigateUp()
        }
        binding.searchLocationListView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchLocationListView.adapter = searchLocationListAdapter
    }

    private fun setupViewListener() {
        binding.searchLocationView.setOnClickListener {
            val key = BuildConfig.KAKAO_REST_API_KEY
            val query = binding.inputLocationView.text.toString()
            storyViewModel.getLocations(key, query)
        }

        binding.inputLocationView.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    binding.searchLocationView.isEnabled = it.isNotEmpty()
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.searchLocationListView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = (binding.searchLocationListView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

                if(lastVisibleItemPosition > 0 && !recyclerView.canScrollVertically(1)) {
                    storyViewModel.getMoreLocations()
                }
            }
        })
    }

    private fun setupObservers() {
        storyViewModel.places.observe(viewLifecycleOwner) {
            it?.let {
                when(it) {
                    is ApiState.Success -> {
                        searchLocationListAdapter.submitList(it.items.places)
                    }
                    is ApiState.Error -> {
                        Snackbar.make(binding.root, getString(R.string.place_search_failure_text), Snackbar.LENGTH_LONG).show()
                    }
                    is ApiState.Loading -> {}
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}