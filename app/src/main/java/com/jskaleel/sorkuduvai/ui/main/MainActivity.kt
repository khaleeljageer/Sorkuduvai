package com.jskaleel.sorkuduvai.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.iammert.library.ui.multisearchviewlib.MultiSearchView
import com.jskaleel.sorkuduvai.databinding.ActivityMainBinding
import com.jskaleel.sorkuduvai.ui.base.QueryDetailsAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MultiSearchView.MultiSearchViewListener {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mainViewModel: MainViewModel by viewModels()
    private val detailsAdapter by lazy {
        QueryDetailsAdapter(this@MainActivity, mutableListOf("Recent"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding.viewPager) {
            this.offscreenPageLimit = 3
            this.adapter = detailsAdapter
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        binding.multiSearchView.setSearchViewListener(this@MainActivity)
    }

    override fun onItemSelected(index: Int, s: CharSequence) {
        Timber.tag("Khaleel").d("onItemSelected == Index : $index Word : $s")
        binding.viewPager.setCurrentItem(index + 1, true)
    }

    override fun onSearchComplete(index: Int, s: CharSequence) {
        Timber.tag("Khaleel").d("onSearchComplete == Index : $index Word : $s")
        mainViewModel.queryWord(baseContext, s.toString())
        detailsAdapter.addItem(s.toString())
        binding.viewPager.setCurrentItem(detailsAdapter.itemCount, true)
    }

    override fun onSearchItemRemoved(index: Int) {
        Timber.tag("Khaleel").d("onSearchItemRemoved == Index : $index")
        detailsAdapter.removeItem(index + 1)
    }

    override fun onTextChanged(index: Int, s: CharSequence) {
        Timber.tag("Khaleel").d("onTextChanged == Index : $index Word : $s")
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}