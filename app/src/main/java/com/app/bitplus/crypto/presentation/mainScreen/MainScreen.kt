package com.app.bitplus.crypto.presentation.mainScreen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.bitplus.R
import com.app.bitplus.crypto.domain.Coin
import com.app.bitplus.crypto.presentation.CoinHistoryState
import com.app.bitplus.crypto.presentation.CoinSharedPreference
import com.app.bitplus.crypto.presentation.Screen
import com.app.bitplus.crypto.presentation.WindowInfo
import com.app.bitplus.crypto.presentation.coin_list.CoinListViewModel
import com.app.bitplus.crypto.presentation.mainScreen.components.CryptoInfo
import com.app.bitplus.crypto.presentation.models.toCoinUi
import com.app.bitplus.crypto.presentation.rememberWindowInfo
import com.app.bitplus.ui.theme.BitPlusTheme
import com.app.bitplus.ui.theme.backgroundDarkHighContrast
import com.app.bitplus.ui.theme.backgroundLightHighContrast
import com.app.bitplus.ui.theme.onTertiaryDark
import com.app.bitplus.ui.theme.onTertiaryLight
import com.app.bitplus.ui.theme.tertiaryDark
import com.app.bitplus.ui.theme.tertiaryLight
import org.koin.androidx.compose.koinViewModel


@Composable
fun MainScreen(
    modifier: Modifier,
    historyState: CoinHistoryState,
    navController: NavController
){

    val windowInfo = rememberWindowInfo()

    val coinSP = CoinSharedPreference
    val context = LocalContext.current

    val tertiaryOnContainer = if(isSystemInDarkTheme()){
        onTertiaryLight
    }else{
        onTertiaryDark
    }


    val tertiaryContainerColor = if(isSystemInDarkTheme()){
        tertiaryLight
    }else{
        tertiaryDark
    }


    val bg =  if(isSystemInDarkTheme()){
        backgroundDarkHighContrast
    }else{
        backgroundLightHighContrast
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(bg),
    ) {
        if (windowInfo.screenWidth is WindowInfo.WindowType.Compact){
            Row(
                modifier = Modifier
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "BitPlus",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Medium,
                    color = tertiaryOnContainer,
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.pie_chart),
                    contentDescription = "chart",
                    tint = tertiaryOnContainer,
                    modifier = Modifier
                        .size(35.dp)
                        .clickable { navController.navigate(Screen.CoinList.route) }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier.padding(top = 120.dp)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            ){
                Text(
                    modifier = Modifier
                        .padding(18.dp)
                        .width(250.dp)
                        .align(Alignment.CenterStart),
                    text = "Stay ahead with real-time currency ",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium,
                    color = tertiaryOnContainer,
                    lineHeight = 34.sp
                )
                Text(
                    modifier = Modifier.padding(start = 170.dp, top = 110.dp),
                    text = "Updates,",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Medium,
                    color = tertiaryContainerColor,
                )
                Text(
                    modifier = Modifier.padding(start = 18.dp, top = 146.dp),
                    text = "right at your fingertips.",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Medium,
                    color = tertiaryContainerColor,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if(coinSP.getCoinName(context) != null){
                CryptoInfo(historyState,windowInfo)
            }
        }else{
            Row(
                modifier = Modifier
                    .padding(top = 20.dp, start = 40.dp, end = 20.dp,bottom = 20.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "BitPlus",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Medium,
                    color = tertiaryOnContainer,
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.pie_chart),
                    contentDescription = "chart",
                    tint = tertiaryOnContainer,
                    modifier = Modifier
                        .size(35.dp)
                        .clickable { navController.navigate(Screen.CoinList.route) }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier.padding(start = 20.dp)
                        .align(Alignment.CenterVertically)
                        .width(400.dp)
                        .fillMaxHeight()
                ){
                    Text(
                        modifier = Modifier
                            .padding(18.dp)
                            .width(250.dp)
                            .align(Alignment.CenterStart),
                        text = "Stay ahead with real-time currency ",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Medium,
                        color = tertiaryOnContainer,
                        lineHeight = 34.sp
                    )
                    Text(
                        modifier = Modifier.padding(start = 175.dp, top = 168.dp),
                        text = "Updates,",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Medium,
                        color = tertiaryContainerColor,
                    )
                    Text(
                        modifier = Modifier.padding(start = 18.dp, top = 200.dp),
                        text = "right at your fingertips.",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Medium,
                        color = tertiaryContainerColor,
                    )
                }
                if(coinSP.getCoinName(context) != null){
                    CryptoInfo(historyState,windowInfo)
                }
            }
        }

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



@PreviewLightDark
@Composable
private fun CoinListScreenPreview(){

    val viewModel = koinViewModel<CoinListViewModel>()
    val navController = rememberNavController()
    val historyState by viewModel.historyState.collectAsStateWithLifecycle()

    BitPlusTheme {
        MainScreen(
            modifier = Modifier,
            historyState = historyState,
            navController = navController
        )
    }
}