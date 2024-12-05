package com.app.bitplus.crypto.data.networking.dto

import kotlinx.serialization.Serializable


@Serializable
data class CoinsHistoryResponseDto(
    val data: List<CoinHistoryDto>
)
