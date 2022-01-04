package com.jskaleel.sorkuduvai.ui.fragments.recent

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.jskaleel.sorkuduvai.R
import com.jskaleel.sorkuduvai.databinding.FragmentRecentSearchBinding
import com.jskaleel.sorkuduvai.utils.viewBinding

class RecentSearchesFragment : Fragment(R.layout.fragment_recent_search) {

    private val binding by viewBinding(FragmentRecentSearchBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtLabel.text = "Recent"
    }

    companion object {
        fun newInstance(position: Int): RecentSearchesFragment {
            return RecentSearchesFragment().apply {
                arguments = bundleOf(
                    POSITION to position
                )
            }
        }

        private const val POSITION = "position"
    }
}