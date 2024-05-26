package com.workspaceandroid.ui.feature.add_phrase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.workspaceandroid.R
import com.workspaceandroid.domain.models.phrase.PhraseInput
import com.workspaceandroid.ui.theme.elevation_4
import com.workspaceandroid.ui.theme.light_gray
import com.workspaceandroid.ui.theme.light_gray2
import com.workspaceandroid.ui.theme.offset_12
import com.workspaceandroid.ui.theme.offset_16
import com.workspaceandroid.ui.theme.white
import com.workspaceandroid.ui.widgets.ActionButton
import com.workspaceandroid.ui.widgets.TextInput
import com.workspaceandroid.ui.widgets.ToolbarComponent

@Composable
fun AddPhraseScreen(
    navController: NavHostController,
    viewModel: AddPhraseViewModel = hiltViewModel(),
) {
    AddPhraseScreen(
        state = viewModel.viewState.collectAsState().value,
        onSaveClick = { viewModel.setEvent(AddPhraseContract.Event.OnSaveButtonClicked) },
        phraseBuilder = { builder ->
            viewModel.setEvent(
                AddPhraseContract.Event.OnPhraseUpdated(builder)
            )
        }
    )

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AddPhraseContract.Effect.Navigation.Back -> navController.popBackStack()
                is AddPhraseContract.Effect.ShowToast -> TODO()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPhraseScreen(
    state: AddPhraseContract.State,
    onSaveClick: () -> Unit,
    phraseBuilder: (PhraseInput.() -> Unit) -> Unit,
) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        color = white
    ) {

        ConstraintLayout(
            Modifier.padding(offset_16)
        ) {

            var textFieldCount by remember { mutableStateOf(0) }
            var isEditableCard by remember { mutableStateOf(false) }

            ToolbarComponent(
                text = stringResource(R.string.search_title)
            )

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                elevation = CardDefaults.cardElevation(defaultElevation = elevation_4)
            ) {
                Column(
                    modifier = Modifier
                        .padding(offset_16),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {

                    TextInput(
                        onInputChanged = {
                            isEditableCard = false
                            phraseBuilder {
                                text = it
                            }
                        },
                        label = stringResource(id = R.string.create_card_phrase_label),
                        placeholderText = stringResource(id = R.string.create_card_phrase_placeholder)
                    )

                    TextInput(
                        inputValue = state.predictedPhrase?.examples?.firstOrNull().orEmpty(),
                        onInputChanged = {
                            phraseBuilder {
                                definition = it
                            }
                        },
                        label = stringResource(id = R.string.create_card_translation_label),
                        placeholderText = stringResource(id = R.string.create_card_translation_placeholder)
                    )

                    TextInput(
                        inputValue = state.predictedPhrase?.definition.orEmpty(),
                        onInputChanged = {
                            phraseBuilder {
                                definition = it
                            }
                        },
                        label = stringResource(id = R.string.create_card_definition_label),
                        placeholderText = stringResource(id = R.string.create_card_definition_placeholder),
                        isEditable = true
                    )

                    Text(
                        text = stringResource(id = R.string.create_card_collection_label)
                    )

                    LazyRow(modifier = Modifier.fillMaxWidth()) {
                        items(state.userCollections) { collectionPair ->
                            InputChip(
                                modifier = Modifier.padding(horizontal = 6.dp),
                                selected = collectionPair.first == state.selectedCollectionChip,
                                onClick = {
                                    phraseBuilder {
                                        selectedCollectionId = collectionPair.first
                                    }
                                },
                                label = { Text(text = collectionPair.second) }
                            )
                        }
                    }

                    ActionButton(
                        painter = painterResource(R.drawable.ic_add_circle_outline),
                        buttonText = stringResource(id = R.string.create_card_add_example),
                        backgroundColor = Color.White,
                        onClick = {
                            textFieldCount++
                            isEditableCard = !isEditableCard
                        }
                    )

                    repeat(textFieldCount) {
                        TextInput(
                            modifier = Modifier.padding(top = offset_12),
                            label = "Example #${it + 1}",
                            placeholderText = stringResource(id = R.string.create_card_example_placeholder),
                            onInputChanged = { exampleText ->
                                phraseBuilder {
                                    examples = mapOf(1 to exampleText)
                                }
                            },
                        )
                    }


                }

                Divider(
                    color = light_gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(5.dp)
                )

                ActionButton(
                    Modifier.padding(start = offset_12),
                    painter = painterResource(R.drawable.ic_save),
                    buttonText = stringResource(id = R.string.create_card_save),
                    fontColor = light_gray2,
                    onClick = { onSaveClick() }
                )

            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CollectionScreenPreview() {
    AddPhraseScreen(
        state = AddPhraseContract.State(

        ),
        onSaveClick = {},
        phraseBuilder = {}
    )
}