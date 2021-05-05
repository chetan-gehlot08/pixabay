package com.pixabay.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.pixabay.domain.model.Images
import com.pixabay.util.Constants
import com.pixabay.R
import com.pixabay.databinding.ActivityImageListBinding
import com.pixabay.ui.adapter.ImageAdapter
import com.pixabay.ui.viewmodels.ImageListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ImageListActivity : AppCompatActivity() {
    private lateinit var searchKey: String
    private lateinit var adapter: ImageAdapter
    private lateinit var binding: ActivityImageListBinding
    private val imageListViewModel: ImageListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_list)
        searchKey = intent.getStringExtra(Constants.SEARCH_KEY)!!
        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        binding.recyclerView.layoutManager = layoutManager
        adapter = ImageAdapter(arrayListOf())
        binding.recyclerView.adapter = adapter
    }
    private fun setupObservers() {
        imageListViewModel.isLoad.observe(this, {
            binding.progressBar.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        })
        imageListViewModel.imageListLiveData.observe(this, {
            binding.recyclerView.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
            it?.let { images -> retrieveList(images) }
        })
        imageListViewModel.errorLiveData.observe(this, {
            binding.recyclerView.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
        imageListViewModel.getImages(searchKey)
    }
    private fun retrieveList(images: Images) {
        if(images.hits.isEmpty())
            binding.noDataTextView.visibility = View.VISIBLE
        adapter.apply {
            refreshList(images.hits)
            notifyDataSetChanged()
        }
    }
}