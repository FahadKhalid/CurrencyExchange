package com.fahad.wiretask.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fahad.wiretask.R

@Composable
fun LoadingView() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition,
            modifier = Modifier.size(20.dp),
            iterations = LottieConstants.IterateForever,
        )
    }
}