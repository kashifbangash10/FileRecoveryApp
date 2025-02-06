//package com.example.filerecoveryapp.ui.fragment
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.filerecoveryapp.databinding.FragmentScanResultOtherFileBinding
//import com.example.filerecoveryapp.ui.Adaptor.folderAdaptor.FolderAdapter
//import com.example.filerecoveryapp.viewmodel.FileViewModel
//import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
//class ScanResultOtherFileFragment : Fragment() {
//
//    private var _binding: FragmentScanResultOtherFileBinding? = null
//    private val binding get() = _binding!!
//    private val viewModel: FileViewModel by viewModels()
//
//    private lateinit var adapter: FolderAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentScanResultOtherFileBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Set up RecyclerView with LinearLayoutManager
//        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
//
//        // Observe the list of folders for other file types (like documents)
//        viewModel.otherFiles.observe(viewLifecycleOwner) { folderList ->
//            // Set up adapter with the list of other files
//            adapter = FolderAdapter(folderList) { folder ->
//                // Handle click on the folder (you can pass folder data to another fragment)
//            }
//            binding.recyclerView.adapter = adapter
//        }
//
//        // Fetch other files (documents, APKs, etc.)
//        viewModel.fetchOtherFiles()
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
