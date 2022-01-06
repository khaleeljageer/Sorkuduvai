package com.jskaleel.sorkuduvai.utils.compose

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.jskaleel.sorkuduvai.R

val iconColor = Color(0xFF777777)
val iconTintColor = ColorFilter.tint(color = iconColor)

@Composable
fun CardArrow(
    degrees: Float,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        content = {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_down),
                contentDescription = "Expandable Arrow",
                modifier = Modifier.rotate(degrees),
                tint = iconColor,
            )
        }
    )
}