package com.example.filerecoveryapp.ui.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filerecoveryapp.databinding.FragmentScanResultBinding
import com.example.filerecoveryapp.ui.folderAdaptor.FolderAdapter
import com.example.filerecoveryapp.ui.folderAdaptor.FolderItem

class ScanResultFragment : Fragment() {

    private var _binding: FragmentScanResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FolderAdapter
    private val args: RecoverPhotoFragmentArgs by navArgs() // Retrieve arguments

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Example data (Replace with real scanned folders)
        val folderList = listOf(
            FolderItem("ALL", "/storage/emulated/0/PLAYit/image2.jpg", 1513),

            FolderItem("Camera", "/storage/emulated/0/DCIM/Camera/photo1.jpg", 1513),
            FolderItem("Screenshots", "/storage/emulated/0/Pictures/Screenshots/image1.jpg", 1513),
        )

        adapter = FolderAdapter(folderList) { folder ->
            // Handle folder click event
        }

        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}