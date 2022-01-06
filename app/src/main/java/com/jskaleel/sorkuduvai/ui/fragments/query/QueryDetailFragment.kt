package com.jskaleel.sorkuduvai.ui.fragments.query

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.material.composethemeadapter.MdcTheme
import com.jskaleel.sorkuduvai.R
import com.jskaleel.sorkuduvai.model.QueryResponse
import com.jskaleel.sorkuduvai.ui.main.MainViewModel
import com.jskaleel.sorkuduvai.utils.compose.EmptyResult
import com.jskaleel.sorkuduvai.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                    QueryDetailsPage(query)
                }
            }
        }
    }

    @Composable
    fun QueryDetailsPage(query: String?, viewModel: MainViewModel = hiltViewModel()) {
        val queryResponse: QueryResponse? by viewModel.queryWord(query)
            .collectAsState(initial = null)
        val isLoading: Boolean by viewModel.loading

        ConstraintLayout {
            val (body, progress) = createRefs()
            when (queryResponse) {
                is QueryResponse.QuerySuccess -> {
                    QuerySuccessContainer(modifier = Modifier.constrainAs(body) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }, queryResponse as QueryResponse.QuerySuccess)
                }
                is QueryResponse.QueryError -> {
                    QueryErrorContainer(modifier = Modifier.constrainAs(body) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
                }
                null -> {
                    CircularProgressIndicator(
                        color = colorResource(R.color.app_primaryDarkColor),
                        modifier = Modifier.height(45.dp).width(45.dp)
                            .constrainAs(progress) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }.visible(isLoading)
                    )
                }
            }
        }

    }

    @Composable
    fun QueryErrorContainer(modifier: Modifier) {
        EmptyResult(text = stringResource(R.string.no_result_found), modifier = modifier)
    }

    @Composable
    fun QuerySuccessContainer(modifier: Modifier, queryResponse: QueryResponse.QuerySuccess) {
        Text(
            modifier = modifier.fillMaxWidth(),
            text = queryResponse.status,
            textAlign = TextAlign.Center
        )
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