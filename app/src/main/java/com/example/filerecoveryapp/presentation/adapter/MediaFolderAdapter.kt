package com.example.filerecoveryapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.filerecoveryapp.R
import com.example.filerecoveryapp.databinding.ItemMediaFolderBinding
import com.example.filerecoveryapp.domain.model.MediaFolder
import com.example.filerecoveryapp.domain.model.MediaType
import java.text.DecimalFormat
import kotlin.math.log10
import kotlin.math.pow

class MediaFolderAdapter(
    private val onFolderClick: (MediaFolder) -> Unit
) : ListAdapter<MediaFolder, MediaFolderAdapter.FolderViewHolder>(FolderDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val binding = ItemMediaFolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FolderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FolderViewHolder(
        private val binding: ItemMediaFolderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(folder: MediaFolder) {
            with(binding) {
                folderName.text = folder.name
                fileCount.text = "${folder.fileCount} files"
                folderSize.text = formatFileSize(folder.totalSize)

                // Set folder icon based on type
                val iconRes = when (folder.type) {
                    MediaType.IMAGE -> R.drawable.recoveryimg
                    MediaType.VIDEO -> R.drawable.videoimg
                    MediaType.DOCUMENT -> R.drawable.recoveryotherfileimg
                    MediaType.AUDIO -> R.drawable.recoverdfilesimg
                    MediaType.OTHER -> R.drawable.recoverdfilesimg
                }
                folderIcon.setImageResource(iconRes)

                // Load preview images for image/video folders
                if (folder.type == MediaType.IMAGE || folder.type == MediaType.VIDEO) {
                    val previewImages = listOf(previewImage1, previewImage2, previewImage3)
                    
                    // Clear previous images
                    previewImages.forEach { imageView ->
                        imageView.setImageResource(R.drawable.videoimg)
                    }

                    // Load up to 3 preview images
                    folder.files.take(previewImages.size).forEachIndexed { index, file ->
                        Glide.with(root.context)
                            .load(file.uri)
                            .placeholder(iconRes)
                            .error(iconRes)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(previewImages[index])
                    }
                }

                root.setOnClickListener {
                    onFolderClick(folder)
                }
            }
        }

        private fun formatFileSize(bytes: Long): String {
            if (bytes <= 0) return "0 B"
            
            val units = arrayOf("B", "KB", "MB", "GB", "TB")
            val digitGroups = (log10(bytes.toDouble()) / log10(1024.0)).toInt()
            
            return DecimalFormat("#,##0.#").format(
                bytes / 1024.0.pow(digitGroups.toDouble())
            ) + " " + units[digitGroups]
        }
    }

    private class FolderDiffCallback : DiffUtil.ItemCallback<MediaFolder>() {
        override fun areItemsTheSame(oldItem: MediaFolder, newItem: MediaFolder): Boolean {
            return oldItem.name == newItem.name && oldItem.type == newItem.type
        }

        override fun areContentsTheSame(oldItem: MediaFolder, newItem: MediaFolder): Boolean {
            return oldItem == newItem
        }
    }
}