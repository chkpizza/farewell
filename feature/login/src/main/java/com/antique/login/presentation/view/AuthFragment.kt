package com.antique.login.presentation.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
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
import com.antique.login.presentation.viewmodel.AuthViewModel
import com.antique.login.R
import com.antique.login.databinding.FragmentAuthBinding
import com.antique.login.di.AuthComponentProvider
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthFragment : Fragment() {
    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val authViewModel by navGraphViewModels<AuthViewModel>(R.id.auth_nav_graph) { viewModelFactory }

    private lateinit var _verificationId: String
    private val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {}
        override fun onVerificationFailed(p0: FirebaseException) {}
        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(verificationId, token)
            _verificationId = verificationId
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as AuthComponentProvider).provideAuthComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_auth, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        setUpInsets()
        setUpViewListener()
        setUpObservers()
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

    private fun setUpViewListener() {
        binding.inputPhoneNumberView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    if(it.length >= 11) {
                        binding.requestCertificationNumberView.background = AppCompatResources.getDrawable(requireActivity(), com.antique.common.R.drawable.shape_enable_button)
                        binding.requestCertificationNumberView.isEnabled = true
                    } else {
                        binding.requestCertificationNumberView.background = AppCompatResources.getDrawable(requireActivity(), com.antique.common.R.drawable.shape_disable_button)
                        binding.requestCertificationNumberView.isEnabled = false
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.inputCertificationNumberView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    if(it.length >= 6) {
                        binding.confirmCertificationNumberView.background = AppCompatResources.getDrawable(requireActivity(),
                            com.antique.common.R.drawable.shape_enable_button
                        )
                        binding.confirmCertificationNumberView.isEnabled = true
                    } else {
                        binding.confirmCertificationNumberView.background = AppCompatResources.getDrawable(requireActivity(),
                            com.antique.common.R.drawable.shape_disable_button
                        )
                        binding.confirmCertificationNumberView.isEnabled = false
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}

        })

        binding.requestCertificationNumberView.setOnClickListener {
            Firebase.auth.setLanguageCode("kr")
            val options = PhoneAuthOptions.newBuilder(Firebase.auth)
                .setPhoneNumber(convertPhoneNumber(binding.inputPhoneNumberView.text.toString()))
                .setTimeout(120L, TimeUnit.SECONDS)
                .setActivity(requireActivity())
                .setCallbacks(callback)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)

            binding.certificationNumberGroup.isVisible = true
            authViewModel.startTimer(120L)
        }

        binding.confirmCertificationNumberView.setOnClickListener {
            val credential = PhoneAuthProvider.getCredential(_verificationId, binding.inputCertificationNumberView.text.toString())
            signInWithPhoneAuthCredential(credential)
        }
    }

    private fun convertPhoneNumber(_phoneNumber: String): String {
        val phoneNumber = _phoneNumber.replace("-", "")
        val firstNumber : String = phoneNumber.substring(0,3)
        var phoneEdit = phoneNumber.substring(3)

        when(firstNumber){
            "010" -> phoneEdit = "+8210$phoneEdit"
            "011" -> phoneEdit = "+8211$phoneEdit"
            "016" -> phoneEdit = "+8216$phoneEdit"
            "017" -> phoneEdit = "+8217$phoneEdit"
            "018" -> phoneEdit = "+8218$phoneEdit"
            "019" -> phoneEdit = "+8219$phoneEdit"
            "106" -> phoneEdit = "+82106$phoneEdit"
        }
        return phoneEdit
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        Firebase.auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                Toast.makeText(requireActivity(), "인증 성공", Toast.LENGTH_SHORT).show()
                /*
                * TODO 인증 성공
                     이미 등록된 사용자인지 검사
                    등록된 사용자가 아닌 경우 -> 프로필 설정 화면으로 이동
                    이미 등록된 사용자인 경우 -> 회원 탈퇴 요청한 사용자인지 검사
                    회원 탈퇴 요청한 사용자가 아닌 경우 -> 곧바로 MainActivity 로 이동
                    회원 탈퇴 요청한 사용자인 경우 -> WithdrawalFragment 로 이동
                * */
                //authViewModel.register(Firebase.auth.currentUser?.uid.toString())
                authViewModel.check(Firebase.auth.currentUser?.uid.toString())
            } else {
                Toast.makeText(requireActivity(), getString(R.string.phone_auth_failure_text), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpObservers() {
        authViewModel.timer.observe(viewLifecycleOwner) {
            if(it <= 0L) {
                binding.requestCertificationNumberView.text = getString(R.string.request_certification_number_view_text)
                binding.inputCertificationNumberView.text.clear()
                binding.certificationNumberGroup.isVisible = false

            } else {
                binding.requestCertificationNumberView.text = "인증번호 다시 받기(${DecimalFormat("00").format(it / 60)}:${DecimalFormat("00").format(it % 60)})"
            }
        }

        authViewModel.navigateToSettings.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(R.id.action_authFragment_to_setupFragment)
        })

        authViewModel.navigateToMain.observe(viewLifecycleOwner, EventObserver {
            LoginState.isLogin.value = SingleEvent(it)
        })

        authViewModel.navigateToWithdrawn.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(R.id.action_authFragment_to_withdrawnCancelFragment)
        })
    }

}