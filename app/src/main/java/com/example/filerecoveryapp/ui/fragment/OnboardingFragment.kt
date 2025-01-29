package com.example.filerecoveryapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.filerecoveryapp.R
import com.example.filerecoveryapp.databinding.FragmentOnboardingBinding
import com.example.filerecoveryapp.ui.OnbordigAdapter.OnboardingAdapter
import com.example.filerecoveryapp.ui.activity.HomeActivity
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingFragment : Fragment() {
    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: OnboardingAdapter
    private val layouts = listOf(
        R.layout.itemintroone,
        R.layout.itemintrotwo,
        R.layout.itemthree
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = OnboardingAdapter(layouts, requireContext())
        binding.viewPager.adapter = adapter

        binding.btnnext.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            if (currentItem < layouts.size - 1) {
                binding.viewPager.currentItem = currentItem + 1
            } else {
                startActivity(Intent(requireContext(),HomeActivity::class.java))
                requireActivity().finish()
            }
        }

        binding.tvSkip.setOnClickListener {
            startActivity(Intent(requireContext(),HomeActivity::class.java))
            requireActivity().finish()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}