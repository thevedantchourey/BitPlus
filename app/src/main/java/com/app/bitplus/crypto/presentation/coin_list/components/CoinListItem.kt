package com.app.bitplus.crypto.presentation.coin_list.components



import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.bitplus.crypto.domain.Coin
import com.app.bitplus.crypto.presentation.models.CoinUi
import com.app.bitplus.crypto.presentation.models.toCoinUi
import com.app.bitplus.ui.theme.BitPlusTheme
import com.app.bitplus.ui.theme.onPrimaryContainerDark
import com.app.bitplus.ui.theme.onPrimaryContainerLight




@Composable
fun CoinListItem(
    coinUi: CoinUi,
    onClick: ()-> Unit,
    modifier: Modifier = Modifier
){

    val primaryOnContainer = if(isSystemInDarkTheme()){
        onPrimaryContainerDark
    }else{
        onPrimaryContainerLight
    }


    Row(
        modifier = modifier
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ){
        Icon(
            imageVector = ImageVector.vectorResource(id = coinUi.iconRes),
            contentDescription = coinUi.name,
            tint = primaryOnContainer,
            modifier = Modifier.size(65.dp)
        )
        Column(
            modifier = Modifier.weight(1f)
        ){
            Text(
                text = coinUi.symbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = primaryOnContainer
            )
            Text(
                text = coinUi.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                color = primaryOnContainer
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$ ${coinUi.priceUsd.formatted}",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = primaryOnContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            PriceChange(
                change = coinUi.changePercent24Hr,
            )
        }
    }
}


@PreviewLightDark
@Composable
private fun CoinListItemPreview(){

    val primaryOnContainerColor = if(isSystemInDarkTheme()){
        onPrimaryContainerLight
    }else{
        onPrimaryContainerDark
    }

    BitPlusTheme {
        CoinListItem(
            coinUi = previewCoin,
            onClick = {},
            modifier = Modifier.background(primaryOnContainerColor)
        )
    }
}


internal val previewCoin = Coin(
    id = "bitcoin",
    rank = 1,
    name = "Bitcoin",
    symbol = "BTC",
    marketCapUsd = 1241273958896.75,
    priceUsd = 62828.15,
    changePercent24Hr = 0.1
).toCoinUi()