package com.example.filerecoveryapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.filerecoveryapp.R
import com.example.filerecoveryapp.databinding.ItemMediaFileBinding
import com.example.filerecoveryapp.domain.model.MediaFile
import com.example.filerecoveryapp.domain.model.MediaType
import java.text.SimpleDateFormat
import java.util.*

class MediaFileAdapter(
    private val onFileClick: (MediaFile) -> Unit,
    private val onFileSelect: (MediaFile, Boolean) -> Unit
) : ListAdapter<MediaFile, MediaFileAdapter.FileViewHolder>(FileDiffCallback()) {

    private val selectedFiles = mutableSetOf<Long>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val binding = ItemMediaFileBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getSelectedFiles(): List<MediaFile> {
        return currentList.filter { selectedFiles.contains(it.id) }
    }

    fun clearSelection() {
        selectedFiles.clear()
        notifyDataSetChanged()
    }

    fun selectAll() {
        selectedFiles.addAll(currentList.map { it.id })
        notifyDataSetChanged()
    }

    inner class FileViewHolder(
        private val binding: ItemMediaFileBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(file: MediaFile) {
            with(binding) {
                fileName.text = file.name
                fileDate.text = formatDate(file.dateAdded)
                
                val isSelected = selectedFiles.contains(file.id)
                selectionCheckbox.isChecked = isSelected
                
                // Load thumbnail based on file type
                when (file.type) {
                    MediaType.IMAGE, MediaType.VIDEO -> {
                        Glide.with(root.context)
                            .load(file.uri)
                            .placeholder(R.drawable.videoimg)
                            .error(R.drawable.videoimg)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(fileThumbnail)
                    }
                    MediaType.DOCUMENT -> {
                        fileThumbnail.setImageResource(R.drawable.recoveryotherfileimg)
                    }
                    MediaType.AUDIO -> {
                        fileThumbnail.setImageResource(R.drawable.recoverdfilesimg)
                    }
                    MediaType.OTHER -> {
                        fileThumbnail.setImageResource(R.drawable.recoverdfilesimg)
                    }
                }

                root.setOnClickListener {
                    onFileClick(file)
                }

                selectionCheckbox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        selectedFiles.add(file.id)
                    } else {
                        selectedFiles.remove(file.id)
                    }
                    onFileSelect(file, isChecked)
                }
            }
        }

        private fun formatDate(timestamp: Long): String {
            val date = Date(timestamp * 1000)
            val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            return formatter.format(date)
        }
    }

    private class FileDiffCallback : DiffUtil.ItemCallback<MediaFile>() {
        override fun areItemsTheSame(oldItem: MediaFile, newItem: MediaFile): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MediaFile, newItem: MediaFile): Boolean {
            return oldItem == newItem
        }
    }
}