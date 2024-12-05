package com.app.bitplus.crypto.domain

import com.app.bitplus.core.domain.util.NetworkError
import com.app.bitplus.core.domain.util.Result

interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
    suspend fun getCoinsHistory(coin: String): Result<List<CoinHistory>, NetworkError>
}