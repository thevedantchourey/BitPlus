package com.app.bitplus.crypto.presentation.coin_list

import com.app.bitplus.crypto.presentation.models.CoinUi

sealed interface CoinListAction {
    data class OnCoinClick(val coinUi: CoinUi?) : CoinListAction
}