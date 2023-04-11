package com.antique.information.presentation.view.information

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.antique.information.R
import com.antique.information.databinding.FragmentInformationBinding
import com.antique.information.domain.model.Preview

class InformationFragment : Fragment() {
    private var _binding: FragmentInformationBinding? = null
    private val binding get() = _binding!!

    private lateinit var recommendListAdapter: RecommendListAdapter
    private lateinit var previewListAdapter: PreviewListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_information, container, false)
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

        val previews = mutableListOf<Preview>().apply {
            add(Preview("https://culture.seoul.go.kr/cmmn/file/imageSrc.do?fileStreCours=35367259ca6485b8ea26e64a6b235a53a15d7fe0395f8c12383db80cbddb43e6&streFileNm=8cad7c53d91e2753bc4f7296a212085d2657b980fbf690d891fc361941a10452", "2023 서울장미축제", "중랑장미공원", true, "2023.05.13~2023.05.28"))
            add(Preview("https://culture.seoul.go.kr/cmmn/file/imageSrc.do?fileStreCours=35367259ca6485b8ea26e64a6b235a5391b248ce4a65fbaaa560d8d241e784e6&streFileNm=cc0629ec7305545a45b1dd1f7fc95edc4200ca52209836814279c2c87fbc6042", "2023 노들섬 반려견 페스티벌 [놀멍뭐하니]", "노들섬 일대", true, "2023.04.01~2023.04.01"))
            add(Preview("https://culture.seoul.go.kr/cmmn/file/imageSrc.do?fileStreCours=35367259ca6485b8ea26e64a6b235a5391b248ce4a65fbaaa560d8d241e784e6&streFileNm=944b4793f178f3a8d8a44f39888d8c006db30ab9edb0bb8e4f449a9903cb06fb", "2023 서울대공원 벚꽃축제 [다시 만나 봄]", "서울대공원 일대", true, "2023.04.05~2023.04.09"))
        }
        previewListAdapter.submitList(previews)
    }
}