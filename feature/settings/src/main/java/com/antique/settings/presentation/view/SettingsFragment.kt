package com.antique.settings.presentation.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.antique.settings.presentation.OnSignOutListener
import com.antique.settings.R
import com.antique.settings.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var onSignOutListener: OnSignOutListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onSignOutListener = context as OnSignOutListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        setupInsets()
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

    private fun setupViewListener() {
        binding.signOutView.setOnClickListener {
            onSignOutListener.signOut()
        }

        binding.openSourceLicenseView.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://kotlin-android.tistory.com/39")))
        }

        binding.privacyPolicyView.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://kotlin-android.tistory.com/40")))
        }
        binding.editProfileView.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_editProfileFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}