package com.jskaleel.sorkuduvai.ui.fragments.recent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.material.composethemeadapter.MdcTheme
import com.jskaleel.sorkuduvai.R
import com.jskaleel.sorkuduvai.db.entities.RecentSearchEntity
import com.jskaleel.sorkuduvai.ui.main.MainViewModel
import com.jskaleel.sorkuduvai.utils.compose.EmptyResult
import com.jskaleel.sorkuduvai.utils.compose.sanaFontFamily
import com.jskaleel.sorkuduvai.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RecentSearchesFragment : Fragment() {

    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                MdcTheme {
                    recentSearchContainer()
                }
            }
        }
    }

    @ExperimentalFoundationApi
    @Preview
    @Composable
    fun recentSearchContainer(viewModel: MainViewModel = hiltViewModel()) {
        val recentSearch: List<RecentSearchEntity> by viewModel.recentSearchList.collectAsState(
            initial = listOf()
        )

        val isLoading: Boolean by viewModel.isLoading

        ConstraintLayout {
            val (body, progress) = createRefs()
            RecentSearchList(modifier = Modifier.constrainAs(body) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, recentSearch)

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

    @ExperimentalFoundationApi
    @Composable
    private fun RecentSearchList(modifier: Modifier, recentSearch: List<RecentSearchEntity>) {
        if (recentSearch.isNullOrEmpty()) {
            EmptyResult(text = stringResource(R.string.no_recent_searches), modifier = modifier)
        } else {
            LazyColumn(modifier = modifier.fillMaxSize()) {
                stickyHeader {
                    Text(
                        text = stringResource(R.string.recent),
                        style = TextStyle(
                            fontFamily = sanaFontFamily,
                            fontWeight = FontWeight.Normal,
                            color = colorResource(R.color.app_primaryDarkColor),
                            fontSize = 18.sp
                        ),
                        modifier = Modifier
                            .background(color = Color.White)
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
                    )
                }

                itemsIndexed(recentSearch) { _, recent ->
                    RecentCard(recent = recent)
                }
            }
        }
    }

    @Composable
    fun RecentCard(
        recent: RecentSearchEntity,
        viewModel: MainViewModel = hiltViewModel()
    ) {
        val context = LocalContext.current
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable { },
            elevation = 3.dp
        ) {
            Box(
                modifier = Modifier.fillMaxSize().padding(15.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterStart),
                    text = recent.word,
                    style = TextStyle(
                        color = Color(0xFF777777),
                        fontSize = 15.sp
                    )
                )

                Image(
                    painterResource(R.drawable.ic_round_clear),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable {
                            viewModel.removeRecentItem(recent.timeStamp)
                        },
                    contentDescription = "",
                    contentScale = ContentScale.Inside,
                    colorFilter = ColorFilter.tint(color = Color(0xFF777777))
                )
            }
        }
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