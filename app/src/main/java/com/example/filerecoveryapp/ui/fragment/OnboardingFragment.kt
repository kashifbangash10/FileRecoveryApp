package com.example.filerecoveryapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.filerecoveryapp.R
import com.example.filerecoveryapp.databinding.FragmentOnboardingBinding
import com.example.filerecoveryapp.ui.OnbordigAdapter.OnboardingAdapter
import com.google.android.material.tabs.TabLayoutMediator


class OnboardingFragment : Fragment() {
    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layouts = listOf(
            R.layout.itemintroone,
            R.layout.itemintrotwo,
            R.layout.itemthree
        )

        val adapter = OnboardingAdapter(layouts, requireContext())
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tablayout, binding.viewPager) { _, _ -> }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}