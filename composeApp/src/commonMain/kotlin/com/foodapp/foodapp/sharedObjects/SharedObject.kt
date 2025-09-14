package com.foodapp.foodapp.sharedObjects


import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.ArcMode
import androidx.compose.animation.core.ExperimentalAnimationSpecApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.ui.geometry.Rect
import kotlin.native.concurrent.ThreadLocal


@ThreadLocal
object SharedObject {
    val baseUrl = "http://10.14.1.116:8080/api"
    var sharedUser: Boolean? = null
    @OptIn(ExperimentalSharedTransitionApi::class, ExperimentalAnimationSpecApi::class)
    val textBoundsTransform = BoundsTransform { initialBounds, targetBounds ->
        keyframes {
            durationMillis = boundsAnimationDurationMillis
            initialBounds at 0 using ArcMode.ArcBelow using FastOutSlowInEasing
            targetBounds at boundsAnimationDurationMillis
        }
    }
    val fadeInObject = fadeIn(
        tween(
            durationMillis = boundsAnimationDurationMillis,
            easing = FastOutSlowInEasing
        ))
    val fadeOutObject = fadeOut(
        tween(
            durationMillis = boundsAnimationDurationMillis,
            easing = FastOutSlowInEasing
        )
    )
    @OptIn(ExperimentalSharedTransitionApi::class)
     val boundsTransform = BoundsTransform { _: Rect, _: Rect ->
        tween(durationMillis = boundsAnimationDurationMillis, easing = FastOutSlowInEasing)
    }
}

private const val boundsAnimationDurationMillis = 500