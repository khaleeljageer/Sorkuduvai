package com.jskaleel.sorkuduvai.ui.fragments.query

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.google.android.material.composethemeadapter.MdcTheme
import com.jskaleel.sorkuduvai.utils.compose.Hello

class QueryDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                MdcTheme {
                    val query = arguments?.getString(QUERY, "")
                    Hello(query)
                }
            }
        }
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