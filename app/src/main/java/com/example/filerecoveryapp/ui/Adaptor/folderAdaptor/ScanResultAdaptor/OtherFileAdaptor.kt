//package com.example.filerecoveryapp.ui.Adaptor.folderAdaptor.ScanResultAdaptor
//
//class OtherFileAdaptor {
//
//    // Data class for OtherFile (document, APK, etc.)
//    data class OtherFileItem(val name: String, val filePaths: List<String>, val fileCount: Int)
//
//    class OtherFileAdaptor(
//        private val files: List<OtherFileItem>,
//        private val listener: (OtherFileItem) -> Unit
//    ) : RecyclerView.Adapter<OtherFileAdaptor.OtherFileViewHolder>() {
//
//        class OtherFileViewHolder(val binding: ItemOtherFileBinding) : RecyclerView.ViewHolder(binding.root)
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherFileViewHolder {
//            val binding = ItemOtherFileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//            return OtherFileViewHolder(binding)
//        }
//
//        override fun onBindViewHolder(holder: OtherFileViewHolder, position: Int) {
//            val file = files[position]
//            with(holder.binding) {
//                folderName.text = file.name
//                textFileCount.text = "${file.fileCount} files"
//
//                val imageViews = listOf(fileThumbnail, fileThumbnail1, fileThumbnail2)
//
//                // Clear previous images
//                imageViews.forEach { it.setImageResource(R.drawable.document_placeholder) }
//
//                // Load file previews (for document, APK, etc.) - You can use a placeholder or custom icon
//                file.filePaths.take(imageViews.size).forEachIndexed { index, filePath ->
//                    // Glide can load a generic file preview for documents or APKs (assuming you have an image placeholder)
//                    Glide.with(root.context)
//                        .load(filePath)
//                        .placeholder(R.drawable.document_placeholder)
//                        .into(imageViews[index])
//                }
//
//                // When folder is clicked, pass the item to listener
//                root.setOnClickListener { listener(file) }
//            }
//        }
//
//        override fun getItemCount() = files.size
//    }
//
//}