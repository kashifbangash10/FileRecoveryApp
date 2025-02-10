package com.example.filerecoveryapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.filerecoveryapp.databinding.FragmentScanRecoveryBinding

class ScanRecoveryFragment : Fragment() {

    private var _binding: FragmentScanRecoveryBinding? = null
    private val binding get() = _binding!!
    //private val args: ScanRecoveryFragmentArgs by navArgs() // SafeArgs se argument receive karein

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanRecoveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding.folderNameTextView.text = args.folderName // Folder ka naam set karein
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
