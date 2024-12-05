package com.app.bitplus.crypto.data.mappers

import com.app.bitplus.crypto.data.networking.dto.CoinDto
import com.app.bitplus.crypto.data.networking.dto.CoinHistoryDto
import com.app.bitplus.crypto.domain.Coin
import com.app.bitplus.crypto.domain.CoinHistory


fun CoinDto.toCoin(): Coin{
    return Coin(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        marketCapUsd = marketCapUsd,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr
    )
}


fun CoinHistoryDto.toCoinHistory(): CoinHistory{
    return CoinHistory(
        priceUsd = priceUsd,
        time = time,
        date = date
    )
}