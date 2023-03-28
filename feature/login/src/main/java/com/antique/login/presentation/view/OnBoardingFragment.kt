package com.antique.login.presentation.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.antique.common.util.EventObserver
import com.antique.common.util.LoginState
import com.antique.common.util.SingleEvent
import com.antique.login.R
import com.antique.login.databinding.FragmentOnBoardingBinding
import com.antique.login.di.AuthComponentProvider
import com.antique.login.presentation.viewmodel.AuthViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class OnBoardingFragment : Fragment() {
    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val authViewModel by navGraphViewModels<AuthViewModel>(R.id.auth_nav_graph) { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as AuthComponentProvider).provideAuthComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_on_boarding, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        setupInsets()
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

    private fun setupViewListener() {
        binding.signInButtonView.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingFragment_to_authFragment)
        }
    }

    private fun setupObservers() {
        authViewModel.registerState.observe(viewLifecycleOwner, EventObserver {
            when(it) {
                true -> {
                    //TODO 3. 로그인도 되어있고 서버에도 등록되어 있음
                    //
                    Log.d("AuthTest", "로그인되어 있고 서버에 등록도 되어있음")
                    LoginState.isLogin.value = SingleEvent(true)
                }
                false -> {
                    //TODO 2 로그인은 되어있지만 사용자가 서버에 등록되어 있지 않음(회원가입 과정에서 이탈한 사용자임)
                    Log.d("AuthTest", "로그인은 되어있지만 서버에 등록되어있지 않음")
                    binding.signInButtonView.isVisible = true
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //TODO 1 로그인이 이미 되어있을 경우 서버에 사용자 정보가 있는지 확인한다
    // 회원가입 과정에서 이탈한 사용자일 경우 발생할 수 있는 버그를 방지하기 위함
    override fun onStart() {
        super.onStart()
        Firebase.auth.currentUser?.let {
            authViewModel.isRegisteredUser(it.uid)
        } ?: run {
            Log.d("AuthTest", "로그인 되어있지 않음")
            binding.signInButtonView.isVisible = true
        }
    }
}