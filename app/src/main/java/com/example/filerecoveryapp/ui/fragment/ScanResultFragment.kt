package com.example.filerecoveryapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filerecoveryapp.databinding.FragmentScanResultBinding
import com.example.filerecoveryapp.ui.Adaptor.folderAdaptor.FolderAdapter
import com.example.filerecoveryapp.viewmodel.FileViewModel
import com.example.filerecoveryapp.viewmodel.ScanState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScanResultFragment : Fragment() {

    private var _binding: FragmentScanResultBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FileViewModel by viewModels()
    private lateinit var adapter: FolderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView setup
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = FolderAdapter(mutableListOf()) { folder ->
            val action = ScanResultFragmentDirections
                .actionScanResultFragmentToScanRecoveryFragment(folder.name)
            findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter

        // Observe scanState changes
        viewModel.scanState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ScanState.Scanning -> {
                    binding.scanningtxt.text = "Scanning..."
                    binding.horizontalProgressBar1.visibility = View.VISIBLE
                    binding.horizontalProgressBar1.progress = state.scanProgress
                    binding.usedStorageText.text = "Scanning: ${state.currentFileName}"
                }

                is ScanState.Success -> {
                    adapter.updateData(state.data)
                    binding.usedStorageText.text = state.data.firstOrNull()?.name ?: "No media found"
                    binding.horizontalProgressBar1.visibility = View.GONE
                }

                is ScanState.Error -> {
                    binding.usedStorageText.text = "Error: ${state.message}"
                    binding.horizontalProgressBar1.visibility = View.GONE
                }

                else -> {
                    binding.usedStorageText.text = "Unknown state"
                    binding.horizontalProgressBar1.visibility = View.GONE
                }
            }
        }

        // Observe scan progress separately
        viewModel.scanProgress.observe(viewLifecycleOwner) { progress ->
            binding.horizontalProgressBar1.visibility = View.VISIBLE
            binding.horizontalProgressBar1.progress = progress
        }

        // Ensure fetchMediaFolders is only called once
        if (viewModel.scanState.value == null) {
            viewModel.fetchMediaFolders()
        }
    }

    override fun onDestroyView() {
        binding.recyclerView.adapter = null
        _binding = null
        super.onDestroyView()
    }
}
