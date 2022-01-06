package com.jskaleel.sorkuduvai.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
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

    private val detailsAdapter by lazy {
        QueryDetailsAdapter(this@MainActivity, mutableListOf("Recent"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding.viewPager) {
            this.offscreenPageLimit = 10
            this.adapter = detailsAdapter
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        binding.multiSearchView.setSearchViewListener(this@MainActivity)
    }

    override fun onItemSelected(index: Int, s: CharSequence) {
        Timber.d("onItemSelected == Index : $index Word : $s")
        binding.viewPager.setCurrentItem(index + 1, true)
    }

    override fun onSearchComplete(index: Int, s: CharSequence) {
        Timber.d("onSearchComplete == Index : $index Word : $s")
        detailsAdapter.addItem(s.toString())
        binding.viewPager.setCurrentItem(detailsAdapter.itemCount, true)
    }

    override fun onSearchItemRemoved(index: Int) {
        Timber.d("onSearchItemRemoved == Index : $index")
        detailsAdapter.removeItem(index + 1)
    }

    override fun onTextChanged(index: Int, s: CharSequence) {
        Timber.d("onTextChanged == Index : $index Word : $s")
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    var backPressedTime: Long = 0
    override fun onBackPressed() {
        if (backPressedTime + 3000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finish()
        } else {
            Snackbar.make(binding.root, "Press back again to exit", Snackbar.LENGTH_LONG).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}