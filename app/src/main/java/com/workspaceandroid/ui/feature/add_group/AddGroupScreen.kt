package com.workspaceandroid.ui.feature.add_group

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.workspaceandroid.R
import com.workspaceandroid.ui.feature.add_phrase.AddPhraseContract
import com.workspaceandroid.ui.feature.add_phrase.AddPhraseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGroupScreen(
    navController: NavHostController,
    viewModel: AddGroupViewModel = hiltViewModel(),
) {
    AddGroupScreen(
        state = viewModel.viewState.collectAsState().value
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGroupScreen(state: AddGroupContract.State) {

}

@Preview(
    backgroundColor = 0xFFFFFF,
    showBackground = true
)
@Composable
private fun AddGroupPreview() {
    AddGroupScreen(
        state = AddGroupContract.State()
    )
}