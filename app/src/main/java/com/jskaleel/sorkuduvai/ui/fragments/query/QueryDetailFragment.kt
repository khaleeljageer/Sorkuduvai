package com.jskaleel.sorkuduvai.ui.fragments.query

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.material.composethemeadapter.MdcTheme
import com.jskaleel.sorkuduvai.R
import com.jskaleel.sorkuduvai.model.DictObject
import com.jskaleel.sorkuduvai.model.QueryResponse
import com.jskaleel.sorkuduvai.ui.main.MainViewModel
import com.jskaleel.sorkuduvai.utils.compose.EmptyResult
import com.jskaleel.sorkuduvai.utils.compose.sanaFontFamily
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
    fun QuerySuccessContainer(
        modifier: Modifier,
        queryResponse: QueryResponse.QuerySuccess,
    ) {
        val dictList = listOf(
            queryResponse.list1,
            queryResponse.list2,
            queryResponse.list3,
            queryResponse.list4,
            queryResponse.list5,
            queryResponse.list6
        )
        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(dictList) {
                ListDictView("செந்தமிழ்ச் சொற்பிறப்பியல் பேரகரமுதலி", queryResponse.list1)
                ListStringView("சொற்பிறப்பியல்", queryResponse.list2)
                ListDictView("தமிழ் இணையக் கல்விக்கழக கலைச்சொல் பேரகராதி", queryResponse.list3)
                ListDictView("ஆட்சிச் சொல் அகராதி", queryResponse.list4)
                ListDictView("சொல் அகராதி", queryResponse.list5)
                ListStringView("", queryResponse.list6)
            }
        }
    }

    @Composable
    fun ListStringView(title: String, list: List<String>) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            elevation = 3.dp
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(15.dp)) {
                Text(
                    text = title,
                    style = TextStyle(
                        color = colorResource(R.color.app_primaryDarkColor),
                        fontFamily = sanaFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Divider(color = colorResource(R.color.app_primaryLightColor), thickness = 1.dp)
                list.forEach {
                    Spacer(modifier = Modifier.height(10.dp))
                    HtmlText(it)
                }
            }
        }
    }

    @Composable
    fun HtmlText(html: String, modifier: Modifier = Modifier) {
        AndroidView(
            modifier = modifier,
            factory = { context -> TextView(context) },
            update = { it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT) },
        )
    }

    @Composable
    fun ListDictView(title: String, list: List<DictObject>) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            elevation = 3.dp
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(15.dp)) {
                Text(
                    text = title,
                    style = TextStyle(
                        color = colorResource(R.color.app_primaryDarkColor),
                        fontFamily = sanaFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Divider(color = colorResource(R.color.app_primaryLightColor), thickness = 1.dp)
                list.forEach {
                    when (it.pos) {
                        "noun" -> {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "பெ. | N.",
                                style = TextStyle(
                                    color = Color(0xFF777777),
                                    fontFamily = sanaFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = it.enWord,
                                style = TextStyle(
                                    color = Color(0xFF777777),
                                    fontFamily = sanaFontFamily,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                            )
                        }
                        "verb" -> {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "வி. | V.",
                                style = TextStyle(
                                    color = Color(0xFF777777),
                                    fontFamily = sanaFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = it.enWord,
                                style = TextStyle(
                                    color = Color(0xFF777777),
                                    fontFamily = sanaFontFamily,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                            )
                        }
                        else -> {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "பிற | Misc.",
                                style = TextStyle(
                                    color = Color(0xFF777777),
                                    fontFamily = sanaFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = it.enWord,
                                style = TextStyle(
                                    color = Color(0xFF777777),
                                    fontFamily = sanaFontFamily,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                            )
                        }
                    }

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