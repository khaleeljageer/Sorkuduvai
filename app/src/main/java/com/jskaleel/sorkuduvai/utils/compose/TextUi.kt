package com.jskaleel.sorkuduvai.utils.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.jskaleel.sorkuduvai.R


@Composable
fun Hello(label: String?) {
    return Text(label ?: "", fontSize = 14.sp)
}

val sanaFontFamily = FontFamily(
    Font(R.font.noto_sans_tamil_light, FontWeight.Light),
    Font(R.font.noto_sans_tamil_regular, FontWeight.Normal),
    Font(R.font.noto_sans_tamil_medium, FontWeight.Medium),
    Font(R.font.noto_sans_tamil_bold, FontWeight.Bold)
)

@Composable
fun EmptyResult(text: String, modifier: Modifier) {
    Text(
        modifier = modifier.fillMaxWidth(), text = text,
        style = TextStyle(
            fontFamily = sanaFontFamily,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF777777),
            fontSize = 18.sp,
            lineHeight = 28.sp
        ),
        textAlign = TextAlign.Center
    )
}