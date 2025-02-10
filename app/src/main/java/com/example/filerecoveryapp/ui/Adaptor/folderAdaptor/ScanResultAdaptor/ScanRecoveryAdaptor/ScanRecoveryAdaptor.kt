package com.example.filerecoveryapp.ui.Adaptor.folderAdaptor.ScanResultAdaptor.ScanRecoveryAdaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.filerecoveryapp.R
import com.example.filerecoveryapp.data.model.ImageItem
import com.example.filerecoveryapp.databinding.ItemgradviewBinding
class ScanRecoveryAdaptor(
    private var imageList: MutableList<ImageItem>,
    private val onItemClick: (ImageItem) -> Unit
) : RecyclerView.Adapter<ScanRecoveryAdaptor.ImageViewHolder>() {

    class ImageViewHolder(val binding: ItemgradviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemgradviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageItem = imageList[position]
        with(holder.binding) {
            Glide.with(root.context)
                .load(imageItem.imageUrl)
                .placeholder(R.drawable.videoimg) // Placeholder image
                .into(imageThumbnail)

            imageThumbnail.setOnClickListener { onItemClick(imageItem) }
        }
    }

    override fun getItemCount(): Int = imageList.size

    fun updateData(newData: List<ImageItem>) {
        imageList.clear()
        imageList.addAll(newData)
        notifyDataSetChanged() // 🔄 Refresh RecyclerView
    }
}
