package com.antique.information.presentation.view.information

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.antique.information.R
import com.antique.information.databinding.FragmentInformationBinding
import com.antique.information.di.InformationComponentProvider
import com.antique.information.domain.model.Preview
import javax.inject.Inject

class InformationFragment : Fragment() {
    private var _binding: FragmentInformationBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val informationViewModel by navGraphViewModels<InformationViewModel>(R.id.information_nav_graph) { viewModelFactory }

    private lateinit var recommendListAdapter: RecommendListAdapter
    private lateinit var previewListAdapter: PreviewListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as InformationComponentProvider).provideInformationComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_information, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.vm = informationViewModel

        initialize()
    }

    private fun initialize() {
        setupInsets()
        setupRecyclerView()
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
        recommendListAdapter = RecommendListAdapter()
        binding.recommendListView.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.recommendListView.adapter = recommendListAdapter

        previewListAdapter = PreviewListAdapter()
        binding.previewListView.layoutManager = LinearLayoutManager(requireActivity())
        binding.previewListView.adapter = previewListAdapter
    }

    private fun setupViewState() {
        val recommends = mutableListOf<String>().apply {
            add("https://culture.seoul.go.kr/cmmn/file/imageSrc.do?fileStreCours=35367259ca6485b8ea26e64a6b235a53a15d7fe0395f8c12383db80cbddb43e6&streFileNm=933c94179370ba4a57351a4f1c460b5139140131461b1ec4fbc79f9f1d2d9b11")
            add("https://culture.seoul.go.kr/cmmn/file/imageSrc.do?fileStreCours=35367259ca6485b8ea26e64a6b235a53a15d7fe0395f8c12383db80cbddb43e6&streFileNm=8cad7c53d91e2753bc4f7296a212085d2657b980fbf690d891fc361941a10452")
            add("https://culture.seoul.go.kr/cmmn/file/imageSrc.do?fileStreCours=35367259ca6485b8ea26e64a6b235a53a15d7fe0395f8c12383db80cbddb43e6&streFileNm=23ba3016835b5be940a31e1f3b308888ad9dfc36288f971a5bacb5e178aa54a2")
            add("https://culture.seoul.go.kr/cmmn/file/imageSrc.do?fileStreCours=35367259ca6485b8ea26e64a6b235a53a15d7fe0395f8c12383db80cbddb43e6&streFileNm=2fb10694f756239f2339e2030dbf11806db30ab9edb0bb8e4f449a9903cb06fb")
        }
        recommendListAdapter.submitList(recommends)

        informationViewModel.fetchPreview()
    }
}