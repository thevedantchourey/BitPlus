package com.app.bitplus.crypto.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun rememberWindowInfo(): WindowInfo {
    val configuration = LocalConfiguration.current
    return WindowInfo(
        screenWidth = when{
            configuration.screenWidthDp < 600 -> WindowInfo.WindowType.Compact
            configuration.screenWidthDp < 840 -> WindowInfo.WindowType.Medium
            else -> WindowInfo.WindowType.Expanded
        },
        screenHeight = when{
            configuration.screenHeightDp < 480 -> WindowInfo.WindowType.Compact
            configuration.screenHeightDp < 900 -> WindowInfo.WindowType.Medium
            else -> WindowInfo.WindowType.Expanded
        },
        screenWidthDp = configuration.screenWidthDp.dp,
        screenHeightDp = configuration.screenHeightDp.dp
    )
}

data class WindowInfo(
    val screenWidth: WindowType,
    val screenHeight: WindowType,
    val screenWidthDp: Dp,
    val screenHeightDp: Dp


){
    sealed class WindowType {
        data object Compact : WindowType()
        data object Medium : WindowType()
        data object Expanded : WindowType()
    }
}