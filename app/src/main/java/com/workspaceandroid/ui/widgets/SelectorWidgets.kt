package com.workspaceandroid.ui.widgets

import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


//@Composable
//fun CheckBoxWithText( //TODO update
//    text: String = "",
//    initialState: Boolean = false,
//    onCheckedChange: () -> Unit
//) {
//    val checkedState = remember { mutableStateOf(initialState) }
//    Checkbox(
//        checked = checkedState.value,
//        onCheckedChange = {
//            checkedState.value = it
//
//        }
//    )
//}