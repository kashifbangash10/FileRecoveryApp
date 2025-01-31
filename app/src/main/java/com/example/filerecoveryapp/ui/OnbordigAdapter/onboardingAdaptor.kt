package com.example.filerecoveryapp.ui.OnbordigAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView

class OnboardingAdapter(
    private val layouts: List<Int>,
    private val context: Context
) : RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    inner class OnboardingViewHolder(private val binding: View) : RecyclerView.ViewHolder(binding) {
        fun bindLayout(layout: Int) {
            val view = LayoutInflater.from(context).inflate(layout, null)
            (binding as ViewGroup).removeAllViews()
            binding.addView(view)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val binding = FrameLayout(parent.context)
        binding.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return OnboardingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bindLayout(layouts[position])
    }

    override fun getItemCount(): Int = layouts.size
}