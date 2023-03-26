package com.antique.story.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.antique.story.R
import com.antique.story.databinding.FragmentStoryDetailsBinding
import com.antique.story.di.StoryComponentProvider
import com.antique.story.viewmodel.StoryViewModel
import javax.inject.Inject

class StoryDetailsFragment : Fragment() {
    private var _binding: FragmentStoryDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: StoryDetailsFragmentArgs by navArgs()
    private val storyId by lazy { args.storyId }

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val storyViewModel by navGraphViewModels<StoryViewModel>(R.id.story_nav_graph) { viewModelFactory }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}