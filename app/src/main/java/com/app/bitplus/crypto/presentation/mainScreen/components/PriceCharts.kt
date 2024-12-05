package com.app.bitplus.crypto.presentation.mainScreen.components


import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.bitplus.crypto.presentation.CoinHistoryState
import com.app.bitplus.crypto.presentation.coin_list.CoinListViewModel
import com.app.bitplus.crypto.presentation.models.toYearFormat
import com.app.bitplus.ui.theme.BitPlusTheme
import com.app.bitplus.ui.theme.onPrimaryContainerDark
import com.app.bitplus.ui.theme.onPrimaryContainerLight
import org.koin.androidx.compose.koinViewModel
import kotlin.math.round
import kotlin.math.roundToInt




@Composable
fun PriceChart(
    modifier: Modifier = Modifier,
    historyState: CoinHistoryState
) {

    val historyList = historyState.coins.takeLast(10)

    val primaryOnContainer = if(isSystemInDarkTheme()){
        onPrimaryContainerDark
    }else{
        onPrimaryContainerLight
    }

    val spacing = 100f
    val transparentGraphColor = remember {
        primaryOnContainer.copy(alpha = 0.5f)
    }

    val upperValue = remember(historyList) {
        (historyList.maxOfOrNull { it.priceUsd }?.plus(1))?.roundToInt() ?: 0
    }
    val lowerValue = remember(historyList) {
        (historyList.minOfOrNull { it.priceUsd })?.toInt() ?: 0
    }

    val density = LocalDensity.current
    val textPaint = remember {
        Paint().apply {
            color = android.graphics.Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    Canvas(modifier = modifier) {
        val spacePerHour = (size.width - spacing) / (historyList.size - 0.5f)
        (0 until historyList.size - 1 step 2).forEach { i ->
            val coin = historyList[i]
            val date = coin.date.toYearFormat()
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    date,
                    spacing + i * spacePerHour,
                    size.height - 5,
                    textPaint
                )
            }
            val priceStep = (upperValue - lowerValue) / 5f
            (1..5).forEach { j ->
                drawContext.canvas.nativeCanvas.apply{
                    drawText(
                        round(lowerValue + priceStep * j).toString(),
                        20f,
                        size.height - spacing - j * size.height / 5f,
                        textPaint
                    )
                }
            }
        }
        var lastX = 0f
        val strokePath = Path().apply {
            val height = size.height
            for (i in historyList.indices) {
                val coin = historyList[i]
                val nextCoin = historyList.getOrNull(i+1) ?: historyList.last()
                val leftRatio = (coin.priceUsd - lowerValue) / (upperValue - lowerValue)
                val rightRatio = (nextCoin.priceUsd -lowerValue) / (upperValue - lowerValue)
                val x1 = spacing + i * spacePerHour
                val y1  =  height - spacing - (leftRatio * height).toFloat()
                val x2 = spacing + (i+1) * spacePerHour
                val y2 = height - spacing - (rightRatio * height).toFloat()
                if(i == 0){ moveTo(x1,y1) }
                lastX = (x1 + x2) / 2f
                quadraticTo(
                    x1, y1,
                    lastX,
                    (y1+y2) / 2f
                )
            }
        }
        val fillPath = android.graphics.Path(strokePath.asAndroidPath())
            .asComposePath().apply {
                lineTo(lastX, size.height - spacing)
                lineTo(spacing, size.height - spacing)
                close()
            }
        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    transparentGraphColor,
                    Color.Transparent
                ),
                endY = size.height - spacing
            )
        )
        drawPath(
            path = strokePath,
            color = primaryOnContainer,
            style = Stroke(
                width = 2.dp.toPx(),
                cap = StrokeCap.Round
            )
        )
    }
}


@PreviewLightDark
@Composable
fun PriceChartsPreview() {

    val viewModel = koinViewModel<CoinListViewModel>()
    val historyState by viewModel.historyState.collectAsStateWithLifecycle()
    BitPlusTheme {
        PriceChart(historyState = historyState)
    }
}