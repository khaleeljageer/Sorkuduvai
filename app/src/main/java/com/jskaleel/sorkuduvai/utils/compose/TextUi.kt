package com.jskaleel.sorkuduvai.utils.compose

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp


@Composable
fun Hello(label: String?) {
    return Text(label ?: "", fontSize = 14.sp)
}