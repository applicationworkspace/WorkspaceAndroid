package com.workspaceandroid.ui.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.workspaceandroid.ui.theme.offset_16
import com.workspaceandroid.ui.theme.offset_8

@Composable
fun ToolbarComponent(
    modifier: Modifier = Modifier,
    text: String = "",
    onSortClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
//        Icon(painter = , contentDescription = )
    }
}