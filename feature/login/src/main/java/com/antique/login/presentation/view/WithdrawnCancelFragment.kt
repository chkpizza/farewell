package com.antique.login.presentation.view

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
import com.antique.common.util.EventObserver
import com.antique.common.util.LoginState
import com.antique.common.util.SingleEvent
import com.antique.login.R
import com.antique.login.databinding.FragmentWithdrawnCancelBinding
import com.antique.login.di.AuthComponentProvider
import com.antique.login.presentation.viewmodel.AuthViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class WithdrawnCancelFragment : Fragment() {
    private var _binding: FragmentWithdrawnCancelBinding? = null
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
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_withdrawn_cancel, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        setUpInsets()
        setupViewListener()
        setupObservers()
    }

    private fun setUpInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            view.updatePadding(
                top = insets.systemWindowInsets.top,
                bottom = insets.systemWindowInsets.bottom
            )
            insets
        }
    }

    private fun setupViewListener() {
        binding.withdrawnCancelView.setOnClickListener {
            authViewModel.withdrawnCancel(Firebase.auth.currentUser?.uid.toString())
        }
    }

    private fun setupObservers() {
        authViewModel.navigateToMain.observe(viewLifecycleOwner, EventObserver {
            when(it) {
                true -> {
                    LoginState.isLogin.value = SingleEvent(true)
                }
                false -> {
                    Snackbar.make(binding.root, getString(R.string.withdrawn_cancel_failure_text), Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }
}