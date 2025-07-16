package com.app.bitplus.crypto.data.networking

import com.app.bitplus.core.data.networking.constructUrl
import com.app.bitplus.core.data.networking.safeCall
import com.app.bitplus.core.domain.util.NetworkError
import com.app.bitplus.core.domain.util.Result
import com.app.bitplus.core.domain.util.map
import com.app.bitplus.crypto.data.mappers.toCoin
import com.app.bitplus.crypto.data.mappers.toCoinHistory
import com.app.bitplus.crypto.data.networking.dto.CoinsHistoryResponseDto
import com.app.bitplus.crypto.data.networking.dto.CoinsResponseDto
import com.app.bitplus.crypto.domain.Coin
import com.app.bitplus.crypto.domain.CoinDataSource
import com.app.bitplus.crypto.domain.CoinHistory
import io.ktor.client.HttpClient
import io.ktor.client.request.get



class RemoteCoinDataSource(
    private val httpClient: HttpClient
): CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets?apiKey=f6214ff1ad756de1294dcb43b24015e69d2e88fa62c840996ad43dcc13c8e3ea")
            )
        }.map {
            response-> response.data.map{ it.toCoin() }
        }
    }

    override suspend fun getCoinsHistory(
        coin: String
    ): Result<List<CoinHistory>, NetworkError> {
        return safeCall<CoinsHistoryResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets/${coin.lowercase()}/history?interval=d1")
            )
        }.map {
                response-> response.data.map{ it.toCoinHistory() }
        }
    }
}
