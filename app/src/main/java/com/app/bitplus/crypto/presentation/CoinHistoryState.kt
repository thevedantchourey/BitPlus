package com.app.bitplus.crypto.presentation

import androidx.compose.runtime.Immutable
import com.app.bitplus.crypto.presentation.models.CoinHistoryUi
import com.app.bitplus.crypto.presentation.models.CoinUi


@Immutable
data class CoinHistoryState(
    val isLoading: Boolean = false,
    val coins: List<CoinHistoryUi> = emptyList(),
    val selectedCoin: CoinUi? = null
)