package com.app.bitplus.crypto.presentation.coin_list

import com.app.bitplus.core.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error: NetworkError): CoinListEvent
}