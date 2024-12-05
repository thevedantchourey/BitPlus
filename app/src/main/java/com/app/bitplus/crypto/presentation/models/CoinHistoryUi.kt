package com.app.bitplus.crypto.presentation.models


import android.icu.text.SimpleDateFormat
import com.app.bitplus.crypto.domain.CoinHistory
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale


data class CoinHistoryUi(
    val priceUsd: Double,
    val time: Long,
    val date: String
)



fun CoinHistory.toCoinHistoryUi(): CoinHistoryUi {
    return CoinHistoryUi(
        priceUsd = priceUsd,
        time = time,
        date = date
    )
}


fun String.toYearFormat(): String {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
        val localDate = LocalDate.parse(this, formatter)
        localDate.year.toString()+ " " + localDate.month.toString().substring(0, 3)
    } catch (e: DateTimeParseException) {
        "Invalid date format" // Or throw an exception
    }
}


