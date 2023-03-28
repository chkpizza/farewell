package com.antique.login.presentation.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navGraphViewModels
import com.antique.common.util.EventObserver
import com.antique.common.util.LoginState
import com.antique.common.util.SingleEvent
import com.antique.login.R
import com.antique.login.databinding.FragmentSetupBinding
import com.antique.login.di.AuthComponentProvider
import com.antique.login.presentation.viewmodel.AuthViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class SetupFragment : Fragment() {
    private var _binding: FragmentSetupBinding? = null
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
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setup, container, false)
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
        binding.inputNickNameView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    if(s.isNotEmpty()) {
                        binding.registerUserView.isEnabled = true
                        binding.registerUserView.background = AppCompatResources.getDrawable(requireActivity(), com.antique.common.R.drawable.shape_enable_button)
                    } else {
                        binding.registerUserView.isEnabled = false
                        binding.registerUserView.background = AppCompatResources.getDrawable(requireActivity(), com.antique.common.R.drawable.shape_enable_button)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}

        })

        binding.registerUserView.setOnClickListener {
            authViewModel.registerUser(
                Firebase.auth.currentUser?.uid.toString(),
                binding.inputNickNameView.text.toString()
            )
        }
    }

    private fun setupObservers() {
        authViewModel.navigateToMain.observe(viewLifecycleOwner, EventObserver {
            when(it) {
                true -> {
                    LoginState.isLogin.value = SingleEvent(true)
                }
                false -> {
                    Snackbar.make(binding.root, getString(R.string.register_user_failure_text), Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }
}