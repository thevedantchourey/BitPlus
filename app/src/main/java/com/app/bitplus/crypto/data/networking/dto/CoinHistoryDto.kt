package com.app.bitplus.crypto.data.networking.dto


import kotlinx.serialization.Serializable

@Serializable
data class CoinHistoryDto(
    val priceUsd: Double,
    val time: Long = 0,
    val date: String = ""
)