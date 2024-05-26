package com.workspaceandroid.ui.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.workspaceandroid.ui.theme.*

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    buttonText: String,
    onClick: (isEnabled: Boolean) -> Unit = {},
    enable: Boolean = true,
    backgroundColor: Color = Color.White,
    fontColor: Color = MaterialTheme.colorScheme.onSurface,
    style: TextStyle = LocalTextStyle.current
) {
    Button(
        onClick = { onClick(enable) },
        modifier = modifier
            .shadow(0.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            hoveredElevation = 0.dp,
            focusedElevation = 0.dp
        ),
        shape = RoundedCornerShape(radius_16),
        contentPadding = PaddingValues(offset_8),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = fontColor
        )
    ) {
        Icon(
            painter = painter,
            modifier = Modifier
                .height(style.lineHeight.value.dp), //TODO refactor
            contentDescription = "drawable_icons",
            tint = fontColor
        )
        Spacer(modifier = Modifier.width(offset_8))
        Text(
            text = buttonText,
            color = fontColor,
            textAlign = TextAlign.Center,
            style = style
        )
    }
}