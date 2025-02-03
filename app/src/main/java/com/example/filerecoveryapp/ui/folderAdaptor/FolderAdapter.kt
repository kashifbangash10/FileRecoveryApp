package com.example.filerecoveryapp.ui.folderAdaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filerecoveryapp.R
import com.example.filerecoveryapp.databinding.ItemscanimagesBinding

data class FolderItem(val name: String, val imagePaths: String, val fileCount: Int)

class FolderAdapter(private val folders: List<FolderItem>, private val listener: (FolderItem) -> Unit) :
    RecyclerView.Adapter<FolderAdapter.FolderViewHolder>() {

    class FolderViewHolder(val binding: ItemscanimagesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val binding = ItemscanimagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FolderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        val folder = folders[position]
        with(holder.binding) {
            foldername.text = folder.name
            textFileCount.text = "${folder.fileCount} files"

            // List of ImageViews to hold the images
            val imageViews = listOf(imageThumbnail, imageThumbnail1, imageThumbnail2)

            // If there are multiple images in the folder, assign them to the ImageViews
            folder.imagePaths.forEachIndexed { index, imagePath ->
                if (index < imageViews.size) {
                    Glide.with(root.context)
                        .load(imagePath)
                        .placeholder(R.drawable.videoimg)
                        .into(imageViews[index])  // Load image into the corresponding ImageView
                }
            }

            root.setOnClickListener { listener(folder) }
        }
    }

    override fun getItemCount() = folders.size
}
