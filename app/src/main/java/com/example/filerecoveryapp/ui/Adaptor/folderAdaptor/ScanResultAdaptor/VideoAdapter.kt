package com.example.filerecoveryapp.ui.Adaptor.folderAdaptor.ScanResultAdaptor

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filerecoveryapp.databinding.ItemsanvideosBinding
import com.example.filerecoveryapp.ui.Adaptor.folderAdaptor.FolderItem

// Data class for video folder
data class VideoFolder(val name: String, val videoPaths: List<String>, val fileCount: Int)

class VideoAdapter(
    private var videoFolders: List<VideoFolder>,
    private val listener: (VideoFolder) -> Unit
) : RecyclerView.Adapter<VideoAdapter.VideoFolderViewHolder>() {

    class VideoFolderViewHolder(val binding: ItemsanvideosBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoFolderViewHolder {
        val binding = ItemsanvideosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoFolderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoFolderViewHolder, position: Int) {
        val folder = videoFolders[position]
        with(holder.binding) {
            foldername.text = folder.name
            textFileCount.text = "${folder.fileCount} videos"

            // Check if there are video paths available
            if (folder.videoPaths.isNotEmpty()) {
                // Load the first video URL into the VideoView
                val videoUri = Uri.parse(folder.videoPaths[0])
                videoView1.setVideoURI(videoUri)
                videoView1.start() // Start the video immediately
            } else {
                // Handle case when no video URLs are available
                videoView1.visibility = View.GONE // Hide VideoView if no videos are available
            }

            // Handle click listener to navigate or show more details
            root.setOnClickListener { listener(folder) }
        }
    }

    override fun getItemCount() = videoFolders.size

    // Function to update data dynamically with the correct data type
    fun updateData(newFolders: List<VideoFolder>) {
        videoFolders = newFolders
        notifyDataSetChanged()
    }
}
