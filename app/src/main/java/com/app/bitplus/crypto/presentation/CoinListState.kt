package com.app.bitplus.crypto.presentation

import androidx.compose.runtime.Immutable
import com.app.bitplus.crypto.presentation.models.CoinUi


@Immutable
data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<CoinUi> = emptyList(),
    val selectedCoin: CoinUi? = null,
)