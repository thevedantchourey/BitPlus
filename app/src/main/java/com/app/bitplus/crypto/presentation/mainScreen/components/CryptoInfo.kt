package com.app.bitplus.crypto.presentation.mainScreen.components


import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.bitplus.R
import com.app.bitplus.crypto.presentation.CoinHistoryState
import com.app.bitplus.crypto.presentation.CoinSharedPreference
import com.app.bitplus.crypto.presentation.WindowInfo
import com.app.bitplus.crypto.presentation.coin_list.CoinListViewModel
import com.app.bitplus.crypto.presentation.coin_list.components.PriceChange
import com.app.bitplus.crypto.presentation.models.toDisplayableNumber
import com.app.bitplus.crypto.presentation.rememberWindowInfo
import com.app.bitplus.ui.theme.BitPlusTheme
import com.app.bitplus.ui.theme.onPrimaryContainerDark
import com.app.bitplus.ui.theme.onPrimaryContainerLight
import com.app.bitplus.ui.theme.onTertiaryDark
import com.app.bitplus.ui.theme.onTertiaryLight
import com.app.bitplus.ui.theme.primaryContainerDark
import com.app.bitplus.ui.theme.primaryContainerLight
import com.app.bitplus.ui.theme.tertiaryDark
import com.app.bitplus.ui.theme.tertiaryLight
import org.koin.androidx.compose.koinViewModel


@Composable
fun CryptoInfo(
    historyState: CoinHistoryState,
    windowInfo: WindowInfo
){

    var name by remember { mutableStateOf("") }
    var symbol by remember { mutableStateOf("") }
    var iconRes by remember { mutableIntStateOf(0) }
    var priceUsd by remember { mutableStateOf("") }
    var changePer24Hr by remember { mutableStateOf(0.0.toDisplayableNumber())}


    val primaryContainerColor = if(isSystemInDarkTheme()){
        primaryContainerDark
    }else{
        primaryContainerLight
    }


    val tertiaryContainerColor = if(isSystemInDarkTheme()){
        tertiaryLight
    }else{
        tertiaryDark
    }


    val tertiaryOnContainer = if(isSystemInDarkTheme()){
        onTertiaryLight
    }else{
        onTertiaryDark
    }


    val primaryOnContainer = if(isSystemInDarkTheme()){
        onPrimaryContainerDark
    }else{
        onPrimaryContainerLight
    }

    val coinSP = CoinSharedPreference
    val context = LocalContext.current

    name = coinSP.getCoinName(context)?: "Bitcoin"
    symbol = coinSP.getCoinSym(context)?: "BTC"
    iconRes =  coinSP.getCoinIcon(context)?.toInt()?: R.drawable.qbit
    priceUsd =  coinSP.getCoinPrice(context)?: "62828.15"
    changePer24Hr =  coinSP.getChangePer24h(context)?.toDouble()?.toDisplayableNumber() ?: 0.1.toDisplayableNumber()

    if (windowInfo.screenWidth is WindowInfo.WindowType.Compact){
        Column(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 30.dp).fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
            ) {
                Row(
                    modifier = Modifier
                        .height(110.dp).width(220.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(tertiaryContainerColor),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = symbol,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = tertiaryOnContainer,
                            maxLines = 1,
                        )
                        Text(
                            text =  if (name.length > 10) name.take(10) else name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Light,
                            color = tertiaryOnContainer,
                            maxLines = 1
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = ImageVector.vectorResource(id = iconRes),
                        contentDescription =  name,
                        tint = tertiaryOnContainer,
                        modifier = Modifier
                            .size(80.dp)
                            .align(Alignment.CenterVertically)
                            .padding(end = 16.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .height(110.dp)
                        .width(220.dp)
                        .padding(start = 10.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(primaryContainerColor),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "$ $priceUsd",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = primaryOnContainer
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        PriceChange(
                            change = changePer24Hr,
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(primaryContainerColor),
            ) {
                if(historyState.isLoading){
                    CircularProgressIndicator(color = primaryOnContainer, modifier = Modifier.align(Alignment.Center))
                }else{
                    PriceChart(
                        modifier = Modifier.padding(start = 32.dp, top = 70.dp, bottom = 20.dp, end = 20.dp).fillMaxSize(),
                        historyState = historyState
                    )
                }
            }
        }
    }else{
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .padding(bottom = 10.dp),
            ) {
                Row(
                    modifier = Modifier
                        .height(110.dp).width(260.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(tertiaryContainerColor),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = symbol,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = tertiaryOnContainer,
                        )
                        Text(
                            text =  if (name.length > 10) name.take(10) else name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Light,
                            color = tertiaryOnContainer,
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = ImageVector.vectorResource(id = iconRes),
                        contentDescription =  name,
                        tint = tertiaryOnContainer,
                        modifier = Modifier
                            .size(70.dp)
                            .align(Alignment.CenterVertically)
                            .padding(end = 16.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .height(110.dp)
                        .width(220.dp)
                        .padding(start = 10.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(primaryContainerColor),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp).fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "$ $priceUsd",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = primaryOnContainer
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        PriceChange(
                            change = changePer24Hr,
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(primaryContainerColor),
            ) {
                if(historyState.isLoading){
                    CircularProgressIndicator(color = primaryOnContainer, modifier = Modifier.align(Alignment.Center))
                }else{
                    PriceChart(
                        modifier = Modifier.padding(start = 32.dp, top = 80.dp, bottom = 20.dp, end = 20.dp).fillMaxSize(),
                        historyState = historyState
                    )
                }
            }
        }
    }

}




@PreviewLightDark
@Composable
private fun CryptoInfoPreview(){

    val windowInfo = rememberWindowInfo()
    val viewModel = koinViewModel<CoinListViewModel>()
    val historyState by viewModel.historyState.collectAsStateWithLifecycle()
    BitPlusTheme {
        CryptoInfo(
            historyState,
            windowInfo,
        )
    }
}


//internal val previewCoin = Coin(
//    id = "bitcoin",
//    rank = 1,
//    name = "Bitcoin",
//    symbol = "BTC",
//    marketCapUsd = 1241273958896.75,
//    priceUsd = 62828.15,
//    changePercent24Hr = 0.1
//).toCoinUi()
