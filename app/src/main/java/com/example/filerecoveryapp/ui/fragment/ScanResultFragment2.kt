package com.example.filerecoveryapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filerecoveryapp.databinding.FragmentScanResult2Binding
import com.example.filerecoveryapp.ui.Adaptor.folderAdaptor.ScanResultAdaptor.VideoAdapter
import com.example.filerecoveryapp.ui.ViewModel.VideoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScanResultFragment2 : Fragment() {

    private var _binding: FragmentScanResult2Binding? = null
    private val binding get() = _binding!!
    private val viewModel: VideoViewModel by viewModels()
    private lateinit var adapter: VideoAdapter // Use VideoAdapter instead of FolderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanResult2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView with LinearLayoutManager
        binding.recyclerView1.layoutManager = LinearLayoutManager(requireContext())

        // Initialize VideoAdapter with an empty list (use immutable list)
        adapter = VideoAdapter(emptyList()) { videoFolder ->
            // Handle item click here if needed
        }
        binding.recyclerView1.adapter = adapter

        // Observe videoFolders LiveData and update the adapter when data changes
        viewModel.videoFolders.observe(viewLifecycleOwner) { folderList ->
            // Update adapter data when the LiveData changes
            adapter.updateData(folderList)
        }

        // Fetch video folders data when the fragment is created
        viewModel.fetchVideoFolders()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
