package com.workspaceandroid.ui.feature.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.workspaceandroid.domain.models.phrase.PhraseModel
import com.workspaceandroid.ui.theme.gray
import com.workspaceandroid.ui.theme.green
import com.workspaceandroid.ui.theme.icon_size_12
import com.workspaceandroid.ui.theme.icon_size_16
import com.workspaceandroid.ui.theme.icon_size_64
import com.workspaceandroid.ui.theme.offset_16
import com.workspaceandroid.ui.theme.offset_32
import com.workspaceandroid.ui.theme.offset_4
import com.workspaceandroid.ui.theme.orange
import com.workspaceandroid.ui.theme.phrase_card_back
import com.workspaceandroid.ui.theme.phrase_card_front
import com.workspaceandroid.ui.theme.radius_8
import com.workspaceandroid.ui.theme.red
import kotlin.math.absoluteValue

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    HomeScreen(
        state = viewModel.viewState.collectAsState().value,
        onPositiveClick = { viewModel.onPositiveCLick(it) },
        onNegativeClick = { viewModel.onNegativeClick(it) },
        onRefreshClick = { viewModel.fetchNewPhrasesForSwipeWidget() }
    )

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeContract.Effect.ShowToast -> TODO()
            }
        }
    }
}

@Composable
fun HomeScreen(
    state: HomeContract.HomeState,
    onPositiveClick: (Long) -> Unit,
    onNegativeClick: (Long) -> Unit,
    onRefreshClick: () -> Unit,
) {

    when (state) {
        is HomeContract.HomeState.Error -> {}
        HomeContract.HomeState.Loading -> {}
        is HomeContract.HomeState.Success -> {
            HorizontalPagerSample(
                state.phrases,
                onPositiveClick,
                onNegativeClick,
                onRefreshClick
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HorizontalPagerSample(
    phrases: List<PhraseModel>,
    onPositiveClick: (Long) -> Unit,
    onNegativeClick: (Long) -> Unit,
    onRefreshClick: () -> Unit,
) {
    val pageCount = phrases.size
    val pagerState = rememberPagerState()
    var currentPhrase = phrases.firstOrNull()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = { onRefreshClick.invoke() },
            colors = ButtonDefaults.buttonColors(orange),
            shape = CircleShape,
            modifier = Modifier.size(icon_size_64),
            contentPadding = PaddingValues(1.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "button"
            )
        }

        Spacer(modifier = Modifier.height(offset_32))

        HorizontalPagerIndicator(
            pageCount = pageCount,
            currentPage = pagerState.currentPage,
            targetPage = pagerState.targetPage,
            currentPageOffsetFraction = pagerState.currentPageOffsetFraction
        )

        HorizontalPager(
            pageCount = pageCount,
            state = pagerState
        ) { page ->
            currentPhrase = phrases[page]
            FlipCard(phrases[page])
        }

        Spacer(modifier = Modifier.height(offset_16))
        Row {
            Button(
                onClick = { onPositiveClick.invoke(currentPhrase?.id ?: -1) },
                colors = ButtonDefaults.buttonColors(green),
                shape = CircleShape,
                modifier = Modifier.size(icon_size_64),
                contentPadding = PaddingValues(1.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "button"
                )
            }
            Spacer(modifier = Modifier.width(offset_16))
            Button(
                onClick = { onNegativeClick.invoke(currentPhrase?.id ?: -1) },
                colors = ButtonDefaults.buttonColors(red),
                shape = CircleShape,
                modifier = Modifier.size(icon_size_64),
                contentPadding = PaddingValues(1.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "button"
                )
            }
        }
    }
}

@Composable
fun FlipCard(phrase: PhraseModel) {
    var rotated by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(500)
    )

    val animateFront by animateFloatAsState(
        targetValue = if (!rotated) 1f else 0f,
        animationSpec = tween(500)
    )

    val animateBack by animateFloatAsState(
        targetValue = if (rotated) 1f else 0f,
        animationSpec = tween(500)
    )

    val animateColor by animateColorAsState(
        targetValue = if (rotated) phrase_card_back else phrase_card_front,
        animationSpec = tween(500)
    )

    Box(
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(offset_16)
                .fillMaxHeight(.3f)
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 8 * density
                }
                .clickable {
                    rotated = !rotated
                },
            border = BorderStroke(2.dp, gray),
            colors = CardDefaults.cardColors(
                containerColor = animateColor
            ),
        ) {

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(offset_16),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(text =
                if (rotated) "${phrase.translation}\n${phrase.definition}\n${phrase.examples.firstOrNull().orEmpty()}" else phrase.text,
                    modifier = Modifier
                        .graphicsLayer {
                            alpha = if (rotated) animateBack else animateFront
                            rotationY = rotation
                        })
            }

        }
    }
}

@Composable
private fun HorizontalPagerIndicator(
    pageCount: Int,
    currentPage: Int,
    targetPage: Int,
    currentPageOffsetFraction: Float,
    modifier: Modifier = Modifier,
    indicatorColor: Color = Color.DarkGray,
    unselectedIndicatorSize: Dp = icon_size_12,
    selectedIndicatorSize: Dp = icon_size_16,
    indicatorCornerRadius: Dp = radius_8,
    indicatorPadding: Dp = offset_4,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .wrapContentSize()
            .height(selectedIndicatorSize + indicatorPadding * 2)
    ) {

        // draw an indicator for each page
        repeat(pageCount) { page ->
            // calculate color and size of the indicator
            val (color, size) =
                if (currentPage == page || targetPage == page) {
                    // calculate page offset
                    val pageOffset =
                        ((currentPage - page) + currentPageOffsetFraction).absoluteValue
                    // calculate offset percentage between 0.0 and 1.0
                    val offsetPercentage = 1f - pageOffset.coerceIn(0f, 1f)

                    val size =
                        unselectedIndicatorSize + ((selectedIndicatorSize - unselectedIndicatorSize) * offsetPercentage)

                    indicatorColor.copy(
                        alpha = offsetPercentage
                    ) to size
                } else {
                    indicatorColor.copy(alpha = 0.1f) to unselectedIndicatorSize
                }

            // draw indicator
            Box(
                modifier = Modifier
                    .padding(
                        // apply horizontal padding, so that each indicator is same width
                        horizontal = ((selectedIndicatorSize + indicatorPadding * 2) - size) / 2,
                        vertical = size / 4
                    )
                    .clip(RoundedCornerShape(indicatorCornerRadius))
                    .background(color)
                    .width(size)
                    .height(size / 2)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    HomeScreen(
        state = HomeContract.HomeState.Success(
            phrases = listOf(
                PhraseModel(
                    id = 1L,
                    createdAt = 1L,
                    formattedDate = "formattedDate",
                    text = "phrase example1",
                    imgUrl = "url",
                    examples = listOf("example1, example2Here"),
                    definition = "definition here",
                    isExpanded = false,
                    translation = "translation",
                    isDone = false,
                    repeatCount = 2
                ),
                PhraseModel(
                    id = 2L,
                    createdAt = 1L,
                    formattedDate = "formattedDate",
                    text = "phrase example2",
                    imgUrl = "url",
                    examples = listOf("example1, example2Here"),
                    definition = "definition here",
                    isExpanded = false,
                    translation = "translation",
                    isDone = false,
                    repeatCount = 2
                )
            )
        ),
        {}, {}, {}
    )
}