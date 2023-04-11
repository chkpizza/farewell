package com.antique.story.presentation.view.place

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.antique.story.BuildConfig
import com.antique.story.R
import com.antique.story.databinding.FragmentPlaceBinding
import com.antique.story.di.StoryComponentProvider
import com.antique.story.presentation.view.add.AddStoryViewModel
import com.antique.story.presentation.view.main.StoryViewModel
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class PlaceFragment : Fragment() {
    private var _binding: FragmentPlaceBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val placeViewModel by lazy { ViewModelProvider(this, viewModelFactory).get(PlaceViewModel::class.java) }
    private val addStoryViewModel by navGraphViewModels<AddStoryViewModel>(R.id.add_story_nav_graph) { viewModelFactory }
    private lateinit var placeListAdapter: PlaceListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as StoryComponentProvider).provide().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_place, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.vm = placeViewModel

        initialize()
    }

    private fun initialize() {
        setupInsets()
        setupRecyclerView()
        setupViewListener()
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
        placeListAdapter = PlaceListAdapter {
            addStoryViewModel.setPlace(it)
            findNavController().navigateUp()
        }
        binding.placeListView.layoutManager = LinearLayoutManager(requireActivity())
        binding.placeListView.adapter = placeListAdapter

        binding.placeListView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = (binding.placeListView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

                if(lastVisibleItemPosition > 0 && !recyclerView.canScrollVertically(1)) {
                    placeViewModel.fetchMorePlaces()
                }
            }
        })
    }

    private fun setupViewListener() {
        binding.searchPlaceView.setOnClickListener {
            val key = BuildConfig.KAKAO_REST_API_KEY
            val query = binding.inputPlaceView.text.toString()
            placeViewModel.fetchPlaces(key, query)
        }

        binding.inputPlaceView.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    binding.searchPlaceView.isEnabled = it.isNotEmpty()
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })
    }

/*    private fun setupObservers() {
        placeViewModel.places.observe(viewLifecycleOwner) {
            when(it) {
                is ApiState.Success -> {
                    placeListAdapter.submitList(it.items.places)
                }
                is ApiState.Error -> {
                    Snackbar.make(binding.root, getString(R.string.place_search_failure_text), Snackbar.LENGTH_LONG).show()
                }
                is ApiState.Loading -> {

                }
            }
        }
    }*/
}