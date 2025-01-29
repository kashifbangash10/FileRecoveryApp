package com.example.filerecoveryapp.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.filerecoveryapp.R
import com.example.filerecoveryapp.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Navigate to Onboarding after 2 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView3, OnboardingFragment())
                .commit()
        }, 2000) // 2 seconds delay
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}