package com.workspaceandroid.ui.screens.collection

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.workspaceandroid.R
import com.workspaceandroid.domain.models.phrase.Phrase
import com.workspaceandroid.domain.models.phrase.UserCollection
import com.workspaceandroid.navigation.Screen
import com.workspaceandroid.ui.theme.*
import com.workspaceandroid.ui.widgets.ActionButton
import com.workspaceandroid.ui.widgets.TextInput
import com.workspaceandroid.ui.widgets.ToolbarComponent
import com.workspaceandroid.utils.EXPAND_ANIMATION_DURATION
import com.workspaceandroid.utils.EXPANSTION_TRANSITION_DURATION
import com.workspaceandroid.utils.noRippleClickable

@Composable
fun CollectionScreen(
    navController: NavController,
    viewModel: CollectionViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    CollectionScreen(state = viewModel.viewState.collectAsState().value,
        onFloatingButtonClick = { navController.navigate(route = Screen.AddPhrase.route) },
        onItemClick = { item ->
            viewModel.setEvent(CollectionContract.Event.OnItemSelected(item))
        },
        onSearch = { text -> viewModel.setEvent(CollectionContract.Event.OnSearchInput(text)) },
        onRemoveClick = { phraseId ->
            viewModel.setEvent(CollectionContract.Event.OnPhraseRemove(phraseId))
        },
        onResetClick = { phraseId ->
            viewModel.setEvent(CollectionContract.Event.OnPhraseReset(phraseId))
        },
        onCollectionClick = { collection -> viewModel.onCollectionSelected(collection) })

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is CollectionContract.Effect.ShowToast ->
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CollectionScreen(
    state: CollectionContract.State,
    onFloatingButtonClick: () -> Unit,
    onItemClick: (Phrase) -> Unit,
    onSearch: (String) -> Unit,
    onRemoveClick: (Long) -> Unit,
    onResetClick: (Long) -> Unit,
    onCollectionClick: (UserCollection) -> Unit,
) {

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = onFloatingButtonClick,
            modifier = Modifier.size(icon_size_64)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "FloatingButton")
        }
    }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            ToolbarComponent(
                modifier = Modifier.padding(start = offset_16, top = offset_16),
                text = stringResource(R.string.collection_title),
            )
            if (!state.isLoading) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(offset_16)
                ) {
                    item {
                        OverviewSection(
                            knownPhrases = state.allCollections
                                .flatMap { it.phrases }
                                .count { it.isDone },
                            phrasesCount = state.allCollections.sumOf { it.phrases.size })
                        Spacer(modifier = Modifier.height(offset_12))
                        UserPacksContainer(
                            state.allCollections,
                            onCollectionClick = { onCollectionClick(it) }
                        )
                        Spacer(modifier = Modifier.height(offset_12))
                    }

                    stickyHeader {
                        SearchBox(onSearch)
                    }

                    items(state.phrases) { phrase ->
                        ExpandableCard(
                            phrase = phrase,
                            onCardClick = { onItemClick(phrase) },
                            expanded = phrase.isExpanded,
                            onRemoveClick = onRemoveClick,
                            onResetClick = onResetClick
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun ExpandableCard(
    phrase: Phrase,
    onCardClick: () -> Unit,
    expanded: Boolean,
    onRemoveClick: (Long) -> Unit,
    onResetClick: (Long) -> Unit
) {
    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }

    val transition = updateTransition(transitionState, label = "")
    val cardElevation by transition.animateDp({
        tween(durationMillis = EXPAND_ANIMATION_DURATION)
    }, label = "") {
        if (expanded) 0.dp else elevation_4
    }
//    val cardRoundedCorners by transition.animateDp({
//        tween(
//            durationMillis = EXPAND_ANIMATION_DURATION,
//            easing = FastOutSlowInEasing
//        )
//    }, label = "") {
//        if (expanded) 0.dp else 16.dp
//    }

    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = EXPAND_ANIMATION_DURATION)
    }, label = "") {
        if (expanded) 180f else 0f
    }
//    val contentColour = remember {
//        Color(ContextCompat.getColor(context, R.color.colowrDayNightPurple))
//    }

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = elevation_2),
        shape = RoundedCornerShape(radius_16),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = offset_8),
        colors = CardDefaults.cardColors(containerColor = white)
    ) {
        Column(Modifier.padding(top = offset_8, start = offset_8, end = offset_8)) {
            Box(modifier = Modifier.noRippleClickable { onCardClick() }) {
                CardTitle(phrase.text, phrase.isDone)
                CardArrow(
                    modifier = Modifier.align(Alignment.TopEnd),
                    degrees = arrowRotationDegree,
                    onClick = onCardClick
                )
            }
            ExpandableContent(phrase = phrase, visible = expanded, onRemoveClick, onResetClick)
        }
    }
}

@Composable
fun CardArrow(
    modifier: Modifier,
    degrees: Float,
    onClick: () -> Unit,
) {
    IconButton(modifier = modifier, onClick = onClick, content = {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_down),
            contentDescription = "Expandable Arrow",
            modifier = Modifier.rotate(degrees),
        )
    })
}

@Composable
fun CardTitle(title: String, isDone: Boolean) {
    Column {
        if (isDone) Icon(Icons.Filled.Done, contentDescription = "Done icon")
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = offset_12, top = offset_12),
            textAlign = TextAlign.Left,
            style = MaterialTheme.typography.titleMedium
        )
        LazyRow(
            modifier = Modifier.padding(
                start = offset_12,
                top = offset_8,
                bottom = offset_12
            )
        ) {

            items(1) {
                CustomChipTitle(text = "Finance", color = blue.copy(alpha = 0.4f))
                Spacer(modifier = Modifier.width(offset_4))
                CustomChipTitle(text = "Macroeconomics", color = orange.copy(alpha = 0.4f))
            }
        }
    }
}

@Composable
fun ExpandableContent(
    phrase: Phrase,
    visible: Boolean = true,
    onRemoveClick: (Long) -> Unit,
    onResetClick: (Long) -> Unit
) {
    val enterTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
        ) + fadeIn(
            initialAlpha = 0.3f,
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
        )
    }
    val exitTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
        ) + fadeOut(
            animationSpec = tween(
                EXPANSTION_TRANSITION_DURATION
            )
        )
    }

    AnimatedVisibility(visible = visible, enter = enterTransition, exit = exitTransition) {
        Column {
            Text(phrase.definition)

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = offset_12)
                    .height(width_1),
                color = light_gray
            )
            Text(text = phrase.examples.joinToString(separator = "\n"))

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(width_1), color = light_gray
            )

            Row {
                ActionButton(painter = painterResource(R.drawable.ic_edit),
                    buttonText = stringResource(id = R.string.label_edit),
                    fontColor = shadow_blue,
                    style = MaterialTheme.typography.labelSmall,
                    onClick = { })

                ActionButton(painter = painterResource(R.drawable.ic_reset),
                    buttonText = stringResource(id = R.string.label_reset),
                    fontColor = shadow_blue,
                    style = MaterialTheme.typography.labelSmall,
                    onClick = { onResetClick(phrase.id) })

                ActionButton(painter = painterResource(R.drawable.ic_remove),
                    buttonText = stringResource(id = R.string.label_remove),
                    fontColor = shadow_blue,
                    style = MaterialTheme.typography.labelSmall,
                    onClick = { onRemoveClick(phrase.id) })
            }

        }
    }
}

@Composable
fun CustomChipTitle(
    text: String,
    color: Color,
) {
    Text(
        modifier = Modifier
            .background(color = color, shape = RoundedCornerShape(20.dp))
            .padding(vertical = offset_8, horizontal = offset_12),
        text = text,
        style = MaterialTheme.typography.labelSmall,
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OverviewSection(
    knownPhrases: Int,
    phrasesCount: Int,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = stringResource(R.string.collection_overview), fontWeight = FontWeight.Bold)
        Row(Modifier.padding(top = offset_16)) {
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = orange),
                shape = RoundedCornerShape(radius_16),
                elevation = CardDefaults.cardElevation(defaultElevation = elevation_4)
            ) {
                Column(
                    modifier = Modifier.padding(offset_12)
                ) {
                    Text(
                        text = "$knownPhrases",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        pluralStringResource(R.plurals.collections, knownPhrases),
                        color = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.width(offset_12))
            Card(
                modifier = Modifier.weight(2f),
                colors = CardDefaults.cardColors(containerColor = blue),
                shape = RoundedCornerShape(radius_16),
                elevation = CardDefaults.cardElevation(defaultElevation = elevation_4)
            ) {
                val wordsString = pluralStringResource(R.plurals.words, phrasesCount)
                Column(modifier = Modifier.padding(offset_12)) {
                    Text("Всього", color = Color.White) //TODO resources
                    Text(text = "$phrasesCount", color = Color.White, fontWeight = FontWeight.Bold)
                    Text("$wordsString в колекціях", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun UserPacksContainer(
    userCollections: List<UserCollection>,
    onCollectionClick: (UserCollection) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(radius_16),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation_4),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {

        var selectedCollectionId by remember { mutableStateOf(-1L) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(offset_16)
        ) {
            Text(
                modifier = Modifier.align(Alignment.TopStart),
                text = stringResource(R.string.collection_my_packs),
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.align(Alignment.TopEnd),
                text = stringResource(R.string.label_see_all),
                fontWeight = FontWeight.Bold
            )
        }

        LazyRow {
            items(userCollections) { collection ->
                val itemSelected = selectedCollectionId == collection.id

                Card(
                    modifier = Modifier
                        .padding(offset_8)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            selectedCollectionId =
                                if (selectedCollectionId != collection.id)
                                    collection.id else -1
                            onCollectionClick.invoke(collection)
                        },
                    shape = RoundedCornerShape(radius_8),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(collection.color)
                    ),
                    border = if (itemSelected) BorderStroke(width_2, Color.Red) else null,

                    ) {
                    Column(
                        Modifier.padding(offset_16)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = collection.phrases.size.toString(),
                            textAlign = TextAlign.Center
                        )
                        Text(text = collection.name, textAlign = TextAlign.Center)
                    }
                }

            }
        }
        Spacer(modifier = Modifier.height(offset_8))
    }
}

@Composable
fun SearchBox(
    onSearch: (String) -> Unit,
) {
//    Card(shape = RoundedCornerShape(radius_16),
//        elevation = CardDefaults.cardElevation(defaultElevation = elevation_4),
//        colors = CardDefaults.cardColors(containerColor = Color.White)) {
//
//    }
    TextInput(
        onInputChanged = { onSearch(it) },
        placeholderText = stringResource(id = R.string.collection_search)
    )
}

@Composable
@Preview(showBackground = true)
fun CollectionScreenPreview() {
//    CollectionScreen(navController = rememberNavController())
    CollectionScreen(
        state = CollectionContract.State(
            allCollections = listOf(
                UserCollection(
                    id = 1,
                    color = -13401857,
                    name = "Top phrases",
                    description = "top phrases here",
                    phrases = emptyList()
                )
            ),
            phrases = listOf(
                Phrase(
                    id = 1L,
                    createdAt = 1L,
                    formattedDate = "formattedDate",
                    text = "phrase example",
                    imgUrl = "url",
                    examples = listOf("example1, example2Here"),
                    definition = "definition here",
                    isExpanded = false,
                    translation = "translation",
                    isDone = true
                )
            )
        ),
        onFloatingButtonClick = { /*TODO*/ },
        onItemClick = {},
        onSearch = {},
        onRemoveClick = {},
        onResetClick = {},
        onCollectionClick = {}
    )
}