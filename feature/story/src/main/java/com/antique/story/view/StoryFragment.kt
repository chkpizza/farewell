package com.antique.story.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.antique.story.R
import com.antique.story.adapter.StoryListAdapter
import com.antique.story.adapter.DoorAdapter
import com.antique.story.adapter.StoryWrapperAdapter
import com.antique.story.databinding.FragmentStoryBinding
import com.google.android.material.snackbar.Snackbar

class StoryFragment : Fragment() {
    private var _binding: FragmentStoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var doorAdapter: DoorAdapter
    private lateinit var storyWrapperAdapter: StoryWrapperAdapter
    private lateinit var storyListAdapter: StoryListAdapter
    private lateinit var concatAdapter: ConcatAdapter

    private val config by lazy {
        ConcatAdapter.Config.Builder().apply {
            setIsolateViewTypes(true)
        }.build()
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
            Toast.makeText(requireActivity(), "$it 클릭됨", Toast.LENGTH_SHORT).show()
        }
        storyWrapperAdapter = StoryWrapperAdapter(storyListAdapter)
        concatAdapter = ConcatAdapter(config, doorAdapter, storyWrapperAdapter)
        binding.storyListView.layoutManager = GridLayoutManager(requireActivity(), 12).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = when(concatAdapter.getItemViewType(position)) {
                    DoorAdapter.VIEW_TYPE -> 12
                    StoryWrapperAdapter.VIEW_TYPE -> 12
                    else -> 12
                }
            }
        }

        binding.storyListView.adapter = concatAdapter
    }

    private fun setupViewState() {
        doorAdapter.submitList(listOf("엔틱보스"))
        storyListAdapter.submitList(listOf(
            "https://file.mk.co.kr/meet/neds/2022/10/image_readtop_2022_927650_16661845165202708.jpeg",
            "https://cdn.topstarnews.net/news/photo/202207/14711972_825784_2919.jpg",
            "https://img.mbn.co.kr/filewww/news/other/2022/11/28/480102484202.jpg",
            "https://i.ytimg.com/vi/uhd-6K2wJiw/maxresdefault.jpg",
            "https://photo.newsen.com/news_photo/2023/01/10/202301101841451810_1.jpg",
            "https://thumbnews.nateimg.co.kr/view610///news.nateimg.co.kr/orgImg/ts/2022/09/28/14747738_972787_5536_org.jpg",
            "https://cdn.topstarnews.net/news/photo/202208/14717756_834826_2020.jpg",
            "https://file.mk.co.kr/meet/neds/2022/10/image_readtop_2022_927650_16661845165202708.jpeg",
            "https://cdn.topstarnews.net/news/photo/202207/14711972_825784_2919.jpg",
            "https://img.mbn.co.kr/filewww/news/other/2022/11/28/480102484202.jpg",
            "https://i.ytimg.com/vi/uhd-6K2wJiw/maxresdefault.jpg",
            "https://photo.newsen.com/news_photo/2023/01/10/202301101841451810_1.jpg",
            "https://thumbnews.nateimg.co.kr/view610///news.nateimg.co.kr/orgImg/ts/2022/09/28/14747738_972787_5536_org.jpg",
            "https://cdn.topstarnews.net/news/photo/202208/14717756_834826_2020.jpg",
            "https://file.mk.co.kr/meet/neds/2022/10/image_readtop_2022_927650_16661845165202708.jpeg",
            "https://cdn.topstarnews.net/news/photo/202207/14711972_825784_2919.jpg",
            "https://img.mbn.co.kr/filewww/news/other/2022/11/28/480102484202.jpg",
            "https://i.ytimg.com/vi/uhd-6K2wJiw/maxresdefault.jpg",
            "https://photo.newsen.com/news_photo/2023/01/10/202301101841451810_1.jpg",
            "https://thumbnews.nateimg.co.kr/view610///news.nateimg.co.kr/orgImg/ts/2022/09/28/14747738_972787_5536_org.jpg",
            "https://cdn.topstarnews.net/news/photo/202208/14717756_834826_2020.jpg",
            "https://file.mk.co.kr/meet/neds/2022/10/image_readtop_2022_927650_16661845165202708.jpeg",
            "https://cdn.topstarnews.net/news/photo/202207/14711972_825784_2919.jpg",
            "https://img.mbn.co.kr/filewww/news/other/2022/11/28/480102484202.jpg",
            "https://i.ytimg.com/vi/uhd-6K2wJiw/maxresdefault.jpg",
            "https://photo.newsen.com/news_photo/2023/01/10/202301101841451810_1.jpg",
            "https://thumbnews.nateimg.co.kr/view610///news.nateimg.co.kr/orgImg/ts/2022/09/28/14747738_972787_5536_org.jpg",
            "https://cdn.topstarnews.net/news/photo/202208/14717756_834826_2020.jpg",
            "https://file.mk.co.kr/meet/neds/2022/10/image_readtop_2022_927650_16661845165202708.jpeg",
            "https://cdn.topstarnews.net/news/photo/202207/14711972_825784_2919.jpg",
            "https://img.mbn.co.kr/filewww/news/other/2022/11/28/480102484202.jpg",
            "https://i.ytimg.com/vi/uhd-6K2wJiw/maxresdefault.jpg",
            "https://photo.newsen.com/news_photo/2023/01/10/202301101841451810_1.jpg",
            "https://thumbnews.nateimg.co.kr/view610///news.nateimg.co.kr/orgImg/ts/2022/09/28/14747738_972787_5536_org.jpg",
            "https://cdn.topstarnews.net/news/photo/202208/14717756_834826_2020.jpg",
            "https://file.mk.co.kr/meet/neds/2022/10/image_readtop_2022_927650_16661845165202708.jpeg",
            "https://cdn.topstarnews.net/news/photo/202207/14711972_825784_2919.jpg",
            "https://img.mbn.co.kr/filewww/news/other/2022/11/28/480102484202.jpg",
            "https://i.ytimg.com/vi/uhd-6K2wJiw/maxresdefault.jpg",
            "https://photo.newsen.com/news_photo/2023/01/10/202301101841451810_1.jpg",
            "https://thumbnews.nateimg.co.kr/view610///news.nateimg.co.kr/orgImg/ts/2022/09/28/14747738_972787_5536_org.jpg",
            "https://cdn.topstarnews.net/news/photo/202208/14717756_834826_2020.jpg"
        ))
    }

    private fun setupViewListener() {
        binding.addStoryView.setOnClickListener {
            findNavController().navigate(R.id.action_storyFragment_to_write_story_nav_graph)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}