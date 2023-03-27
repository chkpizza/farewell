package com.antique.settings.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.antique.common.util.ApiState
import com.antique.settings.R
import com.antique.settings.databinding.FragmentEditProfileBinding
import com.antique.settings.di.SettingsComponentProvider
import com.antique.settings.viewmodel.SettingsViewModel
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val settingsViewModel by lazy { ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as SettingsComponentProvider).provideSettingsComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_edit_profile, container, false)
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
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    if(it.isNotEmpty()) {
                        binding.editProfileSubmitView.isEnabled = true
                        binding.editProfileSubmitView.background = AppCompatResources.getDrawable(requireActivity(), com.antique.common.R.drawable.shape_enable_button)
                    } else {
                        binding.editProfileSubmitView.isEnabled = false
                        binding.editProfileSubmitView.background = AppCompatResources.getDrawable(requireActivity(), com.antique.common.R.drawable.shape_disable_button)
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.editProfileSubmitView.setOnClickListener {
            settingsViewModel.changeNickName(binding.inputNickNameView.text.toString())
        }
    }

    private fun setupObservers() {
        settingsViewModel.changeNickNameState.observe(viewLifecycleOwner) {
            when(it) {
                is ApiState.Success -> {
                    Toast.makeText(requireActivity(), getString(R.string.change_nick_name_success_text), Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
                is ApiState.Error -> {
                    Snackbar.make(binding.root, getString(R.string.change_nick_name_failure_text), Snackbar.LENGTH_LONG).show()
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