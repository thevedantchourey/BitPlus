package com.app.bitplus.crypto.domain

import androidx.compose.runtime.Immutable

@Immutable
data class CoinHistory(
    val priceUsd: Double,
    val time: Long,
    val date: String
)
