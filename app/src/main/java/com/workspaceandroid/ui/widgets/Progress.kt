package com.workspaceandroid.ui.widgets

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun TrackProgress(
    items: Int,
    brush: (from: Int) -> Brush,
    modifier: Modifier = Modifier,
    lineWidth: Dp = 1.dp,
    pathEffect: ((from: Int) -> PathEffect?)? = null,
    icon: @Composable (index: Int) -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(-1f)
        ) {
            val width = drawContext.size.width
            val height = drawContext.size.height

            val yOffset = height / 2
            val itemWidth = width / items

            var startOffset = itemWidth / 2
            var endOffset = startOffset

            val barWidth = lineWidth.toPx()
            repeat(items - 1) {
                endOffset += itemWidth
                drawLine(
                    brush = brush.invoke(it),
                    start = Offset(startOffset, yOffset),
                    end = Offset(endOffset, yOffset),
                    strokeWidth = barWidth,
                    pathEffect = pathEffect?.invoke(it)
                )
                startOffset = endOffset
            }
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(items) { index ->
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    icon.invoke(index)
                }
            }
        }
    }
}