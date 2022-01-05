package com.jskaleel.sorkuduvai.ui.fragments.recent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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
import com.jskaleel.sorkuduvai.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RecentSearchesFragment : Fragment() {

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

    @Composable
    private fun RecentSearchList(modifier: Modifier, recentSearch: List<RecentSearchEntity>) {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(recentSearch) { recent ->
                RecentCard(recent)
            }
        }
    }

    @Composable
    fun RecentCard(recent: RecentSearchEntity) {
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
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                )

                Image(
                    painterResource(R.drawable.ic_round_star_24),
                    modifier = Modifier.align(Alignment.CenterEnd),
                    contentDescription = "",
                    contentScale = ContentScale.Inside
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