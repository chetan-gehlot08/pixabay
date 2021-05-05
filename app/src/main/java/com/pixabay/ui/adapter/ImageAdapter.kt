package com.pixabay.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pixabay.util.Constants
import com.pixabay.R
import com.pixabay.databinding.ItemLayoutBinding
import com.pixabay.domain.model.Hits
import com.pixabay.ui.activity.LargeImagePreviewActivity

class ImageAdapter(private val list: ArrayList<Hits>) :
    RecyclerView.Adapter<ImageAdapter.DataViewHolder>() {

    class DataViewHolder(var itemLayoutBinding: ItemLayoutBinding) : RecyclerView.ViewHolder(itemLayoutBinding.root) {

        fun bind(hits: Hits) {
            itemView.apply {
                Glide.with(itemLayoutBinding.image.context)
                    .load(hits.previewURL)
                    .placeholder(R.drawable.ic_image_placeholder)
                    .into(itemLayoutBinding.image)
                itemLayoutBinding.root.setOnClickListener {
                    context.startActivity(Intent(context,LargeImagePreviewActivity::class.java).putExtra(
                        Constants.LARGE_IMAGE_URL,hits.largeImageURL))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return DataViewHolder(ItemLayoutBinding.bind(view))
    }


    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun refreshList(list1: List<Hits>) {
        this.list.apply {
            clear()
            addAll(list1)
        }
    }
}