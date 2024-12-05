package com.app.bitplus.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.app.bitplus.R


val rubik = FontFamily(
    Font(
        resId = R.font.rubik_light,
        weight = FontWeight.Light,
    ),
    Font(
        resId = R.font.rubik_regular,
        weight = FontWeight.Normal,
    ),
    Font(
        resId = R.font.rubik_bold,
        weight = FontWeight.Bold,
    ),
    Font(
        resId = R.font.rubik_medium,
        weight = FontWeight.Medium,
    )
)



val AppTypography = Typography(
    bodySmall = TextStyle(
        fontFamily = rubik,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = rubik,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = rubik,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    labelMedium = TextStyle(
        fontFamily = rubik,
        fontWeight = FontWeight.SemiBold,
    ),
    headlineMedium = TextStyle(
        fontFamily = rubik,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    )
)