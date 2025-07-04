package com.example.filerecoveryapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.filerecoveryapp.databinding.FragmentMediaFilesBinding
import com.example.filerecoveryapp.domain.model.MediaFile
import com.example.filerecoveryapp.presentation.adapter.MediaFileAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MediaFilesFragment : Fragment() {

    private var _binding: FragmentMediaFilesBinding? = null
    private val binding get() = _binding!!
    
    private val args: MediaFilesFragmentArgs by navArgs()
    private lateinit var fileAdapter: MediaFileAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMediaFilesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupUI()
    }

    private fun setupRecyclerView() {
        fileAdapter = MediaFileAdapter(
            onFileClick = { file ->
                // Handle file click (preview, open, etc.)
                handleFileClick(file)
            },
            onFileSelect = { file, isSelected ->
                // Handle file selection for recovery
                updateSelectionUI()
            }
        )

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = fileAdapter
        }
    }

    private fun setupUI() {
        val folder = args.mediaFolder
        binding.folderTitle.text = folder.name
        binding.fileCount.text = "${folder.fileCount} files"
        
        fileAdapter.submitList(folder.files)
        
        binding.selectAllButton.setOnClickListener {
            fileAdapter.selectAll()
            updateSelectionUI()
        }
        
        binding.recoverButton.setOnClickListener {
            val selectedFiles = fileAdapter.getSelectedFiles()
            if (selectedFiles.isNotEmpty()) {
                // Handle recovery process
                startRecoveryProcess(selectedFiles)
            }
        }
    }

    private fun handleFileClick(file: MediaFile) {
        // Implement file preview/open functionality
        // You can use Intent to open the file or show a preview dialog
    }

    private fun updateSelectionUI() {
        val selectedCount = fileAdapter.getSelectedFiles().size
        binding.selectionCount.text = "$selectedCount selected"
        binding.recoverButton.isEnabled = selectedCount > 0
    }

    private fun startRecoveryProcess(files: List<MediaFile>) {
        // Implement recovery logic
        // This could involve copying files to a recovery folder
        // or marking them as recovered in the database
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}