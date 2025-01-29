package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.foodScreen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.foodapp.core.presentation.Black
import com.foodapp.core.presentation.DarkGrey
import com.foodapp.core.presentation.Green
import com.foodapp.core.presentation.GreenShade
import com.foodapp.core.presentation.TextSize
import com.foodapp.core.presentation.White
import com.foodapp.foodapp.domain.models.Food
import com.foodapp.foodapp.domain.models.FoodCart
import com.foodapp.foodapp.presentation.SnackSharedElementKey
import com.foodapp.foodapp.presentation.SnackSharedElementType
import com.foodapp.foodapp.presentation.components.CustomButton
import com.foodapp.foodapp.presentation.components.FoodItemRow
import com.foodapp.foodapp.presentation.components.applySharedBounds
import com.foodapp.foodapp.presentation.components.applySharedElement
import com.foodapp.foodapp.sharedObjects.SharedObject.boundsTransform
import com.foodapp.foodapp.sharedObjects.SharedObject.fadeInObject
import com.foodapp.foodapp.sharedObjects.SharedObject.fadeOutObject
import com.foodapp.foodapp.sharedObjects.SharedObject.textBoundsTransform
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.http.Url
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.ic__favorite
import kotlinproject.composeapp.generated.resources.ic_favorite_border
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ViewFoodScreenRoot(
    viewModel: ViewFoodScreenViewModel = koinViewModel(), food: Food, restaurantName: String,
    onBackClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedVisibilityScope
) {
    LaunchedEffect(Unit) {
        viewModel.updateFoodItem(food, restaurantName)
    }
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    state?.let { currentState ->
        ViewFoodScreen(state = currentState, onAction = {
            viewModel.onAction(it)
        }, onBackClick = { onBackClick() },
            animatedVisibilityScope = animatedContentScope,
            sharedTransitionScope = sharedTransitionScope
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ViewFoodScreen(
    state: FoodCart,
    onAction: (ViewFoodScreenAction) -> Unit,
    maxImageSize: Dp = 300.dp,
    minImageSize: Dp = 100.dp,
    onBackClick: () -> Unit,
    isFavorite: Boolean = false,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onFavoriteClick: () -> Unit = {}
) {
    var currentImageSize by remember {
        mutableStateOf(maxImageSize)
    }
    var imageScale by remember {
        mutableFloatStateOf(1f)
    }
    var textExpand by remember {
        mutableStateOf(false)
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newImageSize = currentImageSize + delta.dp
                val previousImageSize = currentImageSize
                currentImageSize = newImageSize.coerceIn(minImageSize, maxImageSize)
                val consumed = currentImageSize - previousImageSize
                imageScale = currentImageSize / maxImageSize
                return Offset(0f, consumed.value)
            }
        }
    }
    with(sharedTransitionScope) {
        Box(
            modifier = Modifier.fillMaxSize().nestedScroll(nestedScrollConnection)
                .padding(top = 10.dp)
                .applySharedBounds(state.foodId,SnackSharedElementType.Bounds,animatedVisibilityScope,sharedTransitionScope,
                    boundsTransform
                )
        ) {
            KamelImage(
                { asyncPainterResource(data = Url("https://www.foodiesfeed.com/wp-content/uploads/2023/06/burger-with-melted-cheese.jpg")) },
                contentDescription = "Image",
                modifier = Modifier.applySharedElement(state.foodId,SnackSharedElementType.Image,animatedVisibilityScope,sharedTransitionScope,
                    boundsTransform
                ).size(maxImageSize).clip(RoundedCornerShape(15.dp))
                    .align(Alignment.TopCenter).graphicsLayer {
                        scaleX = imageScale
                        scaleY = imageScale
                        translationY = -(maxImageSize.toPx() - currentImageSize.toPx()) / 2f
                    }, contentScale = ContentScale.FillBounds
            )
            Box(
                modifier = Modifier.padding(10.dp).clip(CircleShape).background(White)
                    .align(Alignment.TopStart).clickable { onBackClick() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = DarkGrey,
                    contentDescription = "arrow",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Box(
                modifier = Modifier.padding(10.dp).clip(CircleShape).background(White)
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    painter = painterResource(if (isFavorite) Res.drawable.ic__favorite else Res.drawable.ic_favorite_border), // Replace with your favorite icon
                    contentDescription = "Favorite",
                    modifier = Modifier
                        .size(35.dp)
                        .align(Alignment.Center)
                        .padding(8.dp).applySharedBounds(state.foodId,SnackSharedElementType.Icon,animatedVisibilityScope,sharedTransitionScope,
                            boundsTransform
                        )
                        .clickable { onFavoriteClick() }
                )
            }

            Column(
                modifier = Modifier.padding(
                    top = currentImageSize + 15.dp,
                    start = 10.dp,
                    end = 10.dp,
                ).verticalScroll(
                    rememberScrollState()
                )
            ) {
                Text(
                    text = state.foodName,
                    color = Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextSize.regular,
                    modifier = Modifier.padding(4.dp).applySharedBounds(state.foodId,SnackSharedElementType.Title,animatedVisibilityScope,sharedTransitionScope,
                        textBoundsTransform
                    ).skipToLookaheadSize()
                )

                Text(
                    text = state.foodTags.joinToString(),
                    color = DarkGrey,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = TextSize.small,
                    modifier = Modifier.padding(4.dp).applySharedBounds(state.foodId,SnackSharedElementType.Tagline,animatedVisibilityScope,sharedTransitionScope,
                        textBoundsTransform
                    ).skipToLookaheadSize()
                )
                HorizontalDivider(thickness = 2.dp, color = GreenShade)

                Text(
                    text = "Description",
                    color = Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextSize.regular,
                    modifier = Modifier.padding(
                        top = 10.dp,
                        bottom = 4.dp,
                        start = 4.dp,
                        end = 4.dp
                    )
                )

                Text(
                    text = state.foodDescription,
                    color = DarkGrey,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = TextSize.small,
                    modifier = Modifier.padding(4.dp).animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    ).clickable {
                        textExpand = !textExpand
                    }, maxLines = if (textExpand) 10 else 2
                )
                HorizontalDivider(thickness = 2.dp, color = GreenShade)

                Text(
                    text = "Add food",
                    color = Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextSize.regular,
                    modifier = Modifier.padding(
                        top = 10.dp,
                        bottom = 4.dp,
                        start = 4.dp,
                        end = 4.dp
                    )
                )

                state.foodCartDetailsList.forEach {
                    FoodItemRow(
                        foodCartDetail = it,
                        onAddClick = { onAction(ViewFoodScreenAction.onAddClick(it)) },
                        onSubClick = { onAction(ViewFoodScreenAction.onSubClick(it)) })
                    HorizontalDivider(thickness = 2.dp, color = GreenShade)
                }
                Spacer(modifier = Modifier.height(30.dp))

            }
            Card(
                modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
                colors = CardDefaults.cardColors(containerColor = White),
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Total Price",
                            color = DarkGrey,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = TextSize.regular,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp)
                        )

                        Text(
                            text = state.totalPrice.toString(),
                            color = Green,
                            fontWeight = FontWeight.Bold,
                            fontSize = TextSize.regular,
                        )
                    }
                    CustomButton(
                        text = "Add to Cart",
                        onClick = { onAction(ViewFoodScreenAction.onCartClick(state)) },
                        buttonColor = Green
                    )
                }
            }
        }
    }
}