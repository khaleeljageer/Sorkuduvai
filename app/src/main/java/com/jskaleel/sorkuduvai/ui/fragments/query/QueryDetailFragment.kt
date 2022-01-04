package com.jskaleel.sorkuduvai.ui.fragments.query

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.jskaleel.sorkuduvai.R
import com.jskaleel.sorkuduvai.databinding.FragmentDetailsBinding
import com.jskaleel.sorkuduvai.utils.viewBinding

class QueryDetailFragment : Fragment(R.layout.fragment_details) {

    private val binding by viewBinding(FragmentDetailsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val query = arguments?.getString(QUERY, "")
        binding.txtLabel.text = query
    }

    companion object {
        fun newInstance(position: Int, query: String): QueryDetailFragment {
            return QueryDetailFragment().apply {
                arguments = bundleOf(
                    POSITION to position,
                    QUERY to query
                )
            }
        }

        private const val POSITION = "position"
        private const val QUERY = "query"
    }
}