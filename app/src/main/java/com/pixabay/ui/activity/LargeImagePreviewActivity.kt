package com.pixabay.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.pixabay.util.Constants
import com.pixabay.R
import com.pixabay.databinding.ActivityLargeImagePreviewBinding
import com.pixabay.util.AppUtil

class LargeImagePreviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLargeImagePreviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_large_image_preview)
        supportActionBar?.hide()
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_large_image_preview)
        if (!AppUtil.isNetworkAvailable(this)) {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show()
        }
        loadingImage()
    }

    private fun loadingImage(){
        val imageUrl = intent?.getStringExtra(Constants.LARGE_IMAGE_URL)
        Glide.with(this)
            .load(imageUrl)
            .placeholder(AppUtil.createLoadingFeature(this))
            .into(binding.fullImageView)
    }
}