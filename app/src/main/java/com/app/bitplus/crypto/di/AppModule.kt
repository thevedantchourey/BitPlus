package com.app.bitplus.crypto.di

import com.app.bitplus.core.data.networking.HttpClientFactory
import com.app.bitplus.crypto.data.networking.RemoteCoinDataSource
import com.app.bitplus.crypto.domain.CoinDataSource
import com.app.bitplus.crypto.presentation.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module



val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()
    viewModelOf(::CoinListViewModel)
}