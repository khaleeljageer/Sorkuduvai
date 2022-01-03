package com.jskaleel.sorkuduvai

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.iammert.library.ui.multisearchviewlib.MultiSearchView
import com.jskaleel.sorkuduvai.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MultiSearchView.MultiSearchViewListener {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        binding.multiSearchView.setSearchViewListener(this@MainActivity)
    }

    override fun onItemSelected(index: Int, s: CharSequence) {

    }

    override fun onSearchComplete(index: Int, s: CharSequence) {

    }

    override fun onSearchItemRemoved(index: Int) {

    }

    override fun onTextChanged(index: Int, s: CharSequence) {

    }
}