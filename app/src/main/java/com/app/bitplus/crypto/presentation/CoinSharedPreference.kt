package com.app.bitplus.crypto.presentation

import android.content.Context
import android.content.SharedPreferences


class CoinSharedPreference {
    companion object {
        private const val PREF_ICON = ""
        private const val PREF_NAME = "crypto"
        private const val PREF_SYM = "Coin"
        private const val PREF_PRICE_USD = "00.0"
        private const val PREF_CHANGE_PERCENT = "0.0"

        private fun getSharedPreferences(ctx: Context): SharedPreferences {
            return ctx.getSharedPreferences(PREF_SYM, Context.MODE_PRIVATE)
        }

        fun setCoinData(ctx: Context, symbol: String, name: String, icon: String,price: String,percent: String) {
            val editor = getSharedPreferences(ctx).edit()
            editor.putString(PREF_SYM, symbol)
            editor.putString(PREF_NAME, name)
            editor.putString(PREF_ICON, icon)
            editor.putString(PREF_PRICE_USD, price)
            editor.putString(PREF_CHANGE_PERCENT, percent)
            editor.apply()
        }

        fun getCoinSym(ctx: Context): String? {
            return getSharedPreferences(ctx).getString(PREF_SYM, null)
        }

        fun getCoinName(ctx: Context): String? {
            return getSharedPreferences(ctx).getString(PREF_NAME, null)
        }

        fun getCoinIcon(ctx: Context): String? {
            return getSharedPreferences(ctx).getString(PREF_ICON, null)
        }

        fun getCoinPrice(ctx: Context): String? {
            return getSharedPreferences(ctx).getString(PREF_PRICE_USD, null)
        }

        fun getChangePer24h(ctx: Context): String? {
            return getSharedPreferences(ctx).getString(PREF_CHANGE_PERCENT, null)
        }


        fun clearCoinData(ctx: Context) {
            val editor = getSharedPreferences(ctx).edit()
            editor.remove(PREF_SYM)
            editor.remove(PREF_NAME)
            editor.remove(PREF_ICON)
            editor.remove(PREF_PRICE_USD)
            editor.remove(PREF_CHANGE_PERCENT)
            editor.apply()
        }

    }
}