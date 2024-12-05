package com.app.bitplus.crypto.presentation

sealed class Screen(
    val route: String
) {
    data object Home: Screen("home")
    data object CoinList: Screen("coinList")
    data object CoinDetailed: Screen("coinDetailed")
}