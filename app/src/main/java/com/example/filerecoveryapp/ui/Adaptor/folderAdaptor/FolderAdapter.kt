package com.example.filerecoveryapp.ui.Adaptor.folderAdaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.filerecoveryapp.R
import com.example.filerecoveryapp.databinding.ItemscanimagesBinding

data class FolderItem(val name: String, val imagePaths: List<String>, val fileCount: Int)

class FolderAdapter(private var folders: List<FolderItem>, private val listener: (FolderItem) -> Unit) :
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

            val imageViews = listOf(imageThumbnail, imageThumbnail1, imageThumbnail2)

            // Clear images to prevent flickering
            imageViews.forEach { it.setImageResource(R.drawable.videoimg) }

            // Load up to 3 images
            folder.imagePaths.take(imageViews.size).forEachIndexed { index, imagePath ->
                Glide.with(root.context)
                    .load(imagePath)
                    .placeholder(R.drawable.videoimg)
                    .diskCacheStrategy(DiskCacheStrategy.ALL) // ✅ Caching for better performance
                    .into(imageViews[index])
            }

            root.setOnClickListener { listener(folder) }
        }
    }

    override fun getItemCount(): Int = folders.size

    fun updateData(newData: List<FolderItem>) {
        folders = newData
        notifyDataSetChanged()
    }
}
