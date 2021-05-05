package com.pixabay.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.pixabay.util.Constants
import com.pixabay.R
import com.pixabay.databinding.ActivitySearchBinding
import com.pixabay.util.AppUtil

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        supportActionBar?.hide()
        binding.searchIcon.setOnClickListener {
            if (binding.searchKeyEditText.text.toString().isEmpty()) {
                Toast.makeText(this, getString(R.string.validate_search_text), Toast.LENGTH_LONG)
                    .show()
            } else {
                if (AppUtil.isNetworkAvailable(this)) {
                    startActivity(
                        Intent(
                            this,
                            ImageListActivity::class.java
                        ).putExtra(Constants.SEARCH_KEY, binding.searchKeyEditText.text.toString())
                    )
                } else {
                    Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}