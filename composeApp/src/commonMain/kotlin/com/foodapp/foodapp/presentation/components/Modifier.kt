package com.foodapp.foodapp.presentation.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.foodapp.foodapp.presentation.SnackSharedElementKey
import com.foodapp.foodapp.presentation.SnackSharedElementType
import com.foodapp.foodapp.sharedObjects.SharedObject.boundsTransform
import com.foodapp.foodapp.sharedObjects.SharedObject.fadeInObject
import com.foodapp.foodapp.sharedObjects.SharedObject.fadeOutObject

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
fun Modifier.applySharedBounds(
    snackId: String,
    type: SnackSharedElementType,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    boundsTransform: BoundsTransform
): Modifier {
    with(sharedTransitionScope) {
        return sharedBounds(
            rememberSharedContentState(
                key = SnackSharedElementKey(
                    snackId = snackId,
                    origin = "restaurant",
                    type = type
                )
            ),
            animatedVisibilityScope = animatedVisibilityScope,
            resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
            boundsTransform = boundsTransform,
            enter = fadeInObject,
                    exit = fadeOutObject,
            placeHolderSize = SharedTransitionScope.PlaceHolderSize.animatedSize
        )
    }
}
@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
fun Modifier.applySharedElement(
    snackId: String,
    type: SnackSharedElementType,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    boundsTransform: BoundsTransform
): Modifier {
    with(sharedTransitionScope) {
        return sharedElement(
            rememberSharedContentState(
                key = SnackSharedElementKey(
                    snackId = snackId,
                    origin = "restaurant",
                    type = type
                )
            ),
            animatedVisibilityScope = animatedVisibilityScope,
            boundsTransform = boundsTransform,
        )
    }
}