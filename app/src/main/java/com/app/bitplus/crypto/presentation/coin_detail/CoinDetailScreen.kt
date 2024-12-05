package com.app.bitplus.crypto.presentation.coin_detail




import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.bitplus.R
import com.app.bitplus.crypto.presentation.CoinListState
import com.app.bitplus.crypto.presentation.CoinSharedPreference
import com.app.bitplus.crypto.presentation.WindowInfo
import com.app.bitplus.crypto.presentation.coin_detail.components.InfoCard
import com.app.bitplus.crypto.presentation.coin_list.CoinListAction
import com.app.bitplus.crypto.presentation.coin_list.CoinListViewModel
import com.app.bitplus.crypto.presentation.mainScreen.previewCoin
import com.app.bitplus.crypto.presentation.models.CoinUi
import com.app.bitplus.crypto.presentation.models.toDisplayableNumber
import com.app.bitplus.crypto.presentation.rememberWindowInfo
import com.app.bitplus.ui.theme.BitPlusTheme
import com.app.bitplus.ui.theme.backgroundDarkHighContrast
import com.app.bitplus.ui.theme.backgroundLightHighContrast
import com.app.bitplus.ui.theme.greenBackground
import com.app.bitplus.ui.theme.onTertiaryDark
import com.app.bitplus.ui.theme.onTertiaryLight
import com.app.bitplus.ui.theme.tertiaryDark
import com.app.bitplus.ui.theme.tertiaryLight
import org.koin.androidx.compose.koinViewModel




@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CoinDetailScreen(
    modifier: Modifier,
    state: CoinListState,
    viewModel: CoinListViewModel,
    coinUi: CoinUi,
    onAction: (CoinListAction) -> Unit,
){

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

    var selected by remember {
        mutableStateOf(false)
    }
    var coinIcon by remember {
        mutableIntStateOf(
            R.drawable.circle
        )
    }
    coinIcon = if(selected){
        R.drawable.approve
    }else{
        R.drawable.circle
    }

    BackHandler {
        onAction(CoinListAction.OnCoinClick(null))
    }
    val windowInfo = rememberWindowInfo()


    if(state.isLoading){
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(bg),
            contentAlignment = Alignment.Center){
            CircularProgressIndicator(
                color = tertiaryOnContainer
            )
        }
    }else if(state.selectedCoin != null){
        val coin = state.selectedCoin

        coinIcon = if(CoinSharedPreference.getCoinSym(LocalContext.current) == coin.symbol){
            R.drawable.approve
        }else{
            R.drawable.circle
        }

        if(selected){
            CoinSharedPreference.setCoinData(
                ctx = LocalContext.current,
                symbol = coin.symbol,
                name = coin.name,
                icon = coin.iconRes.toString(),
                price = coin.priceUsd.formatted,
                percent = coin.changePercent24Hr.value.toString()
            )
            viewModel.loadCoinsHistory(LocalContext.current)
            Toast.makeText(LocalContext.current, "Coin Added to Main Screen", Toast.LENGTH_SHORT).show()
        }

        if(windowInfo.screenWidth is WindowInfo.WindowType.Compact){
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(bg)
                    .verticalScroll(rememberScrollState()),
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.back_arrow),
                        contentDescription = "back",
                        tint = tertiaryOnContainer,
                        modifier = Modifier
                            .size(35.dp)
                            .clickable { onAction(CoinListAction.OnCoinClick(null)) }
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "Coins",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Medium,
                        color = tertiaryOnContainer,
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(top = 40.dp, end = 20.dp, start = 20.dp)
                        .height(90.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(tertiaryContainerColor),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier
                            .padding(start = 18.dp)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = coinUi.symbol,
                            fontSize = 23.sp,
                            fontWeight = FontWeight.Medium,
                            color = tertiaryOnContainer,
                        )
                        Text(
                            text = coinUi.name,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Light,
                            color = tertiaryOnContainer,
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = ImageVector.vectorResource(id = coinUi.iconRes),
                        contentDescription = coinUi.name,
                        tint = tertiaryOnContainer,
                        modifier = Modifier
                            .size(60.dp)
                            .align(Alignment.CenterVertically)
                            .padding(end = 16.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        modifier = Modifier
                            .clickable { selected = !selected }
                            .size(40.dp)
                            .padding(top = 18.dp, end = 18.dp),
                        imageVector = ImageVector.vectorResource(id = coinIcon),
                        contentDescription = "selected",
                        tint = Color.White
                    )
                }
                Spacer(Modifier.weight(1f))
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 60.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    InfoCard(
                        title = stringResource(id = R.string.market_cap),
                        formattedText = "$ ${coin.marketCapUsd.formatted}",
                        icon = ImageVector.vectorResource(id = R.drawable.stock),
                        flowColor = null
                    )
                    InfoCard(
                        title = stringResource(id = R.string.price),
                        formattedText = "$ ${coin.priceUsd.formatted}",
                        icon = ImageVector.vectorResource(id = R.drawable.dollar),
                        flowColor = null
                    )
                    val absoluteChangeFormatted = (coin.priceUsd.value * (coin.changePercent24Hr.value / 100)).toDisplayableNumber()
                    val isPositive = coin.changePercent24Hr.value > 0.0
                    val contentColor = if(isPositive){
                        if (isSystemInDarkTheme()) Color.Green else greenBackground
                    }else{
                        MaterialTheme.colorScheme.error
                    }
                    InfoCard(
                        title = stringResource(id = R.string.change_last_24h),
                        formattedText = absoluteChangeFormatted.formatted,
                        icon = ImageVector.vectorResource(id = if(isPositive){ R.drawable.trending }else{ R.drawable.trending_down }),
                        flowColor = contentColor
                    )
                }
            }
        }else{
            Column(
                modifier = modifier.padding(start = 40.dp)
                    .fillMaxSize()
                    .background(bg)
                    .verticalScroll(rememberScrollState()),
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.back_arrow),
                        contentDescription = "back",
                        tint = tertiaryOnContainer,
                        modifier = Modifier
                            .size(35.dp)
                            .clickable { onAction(CoinListAction.OnCoinClick(null)) }
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "Coins",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Medium,
                        color = tertiaryOnContainer,
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(top = 20.dp, end = 20.dp, start = 20.dp)
                        .height(90.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(tertiaryContainerColor),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier
                            .padding(start = 18.dp)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = coinUi.symbol,
                            fontSize = 23.sp,
                            fontWeight = FontWeight.Medium,
                            color = tertiaryOnContainer,
                        )
                        Text(
                            text = coinUi.name,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Light,
                            color = tertiaryOnContainer,
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = ImageVector.vectorResource(id = coinUi.iconRes),
                        contentDescription = coinUi.name,
                        tint = tertiaryOnContainer,
                        modifier = Modifier
                            .size(60.dp)
                            .align(Alignment.CenterVertically)
                            .padding(end = 16.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        modifier = Modifier
                            .clickable { selected = !selected }
                            .size(40.dp)
                            .padding(top = 18.dp, end = 18.dp),
                        imageVector = ImageVector.vectorResource(id = coinIcon),
                        contentDescription = "selected",
                        tint = Color.White
                    )
                }
                Spacer(Modifier.weight(1f))
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 0.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    InfoCard(
                        title = stringResource(id = R.string.market_cap),
                        formattedText = "$ ${coin.marketCapUsd.formatted}",
                        icon = ImageVector.vectorResource(id = R.drawable.stock),
                        flowColor = null
                    )
                    InfoCard(
                        title = stringResource(id = R.string.price),
                        formattedText = "$ ${coin.priceUsd.formatted}",
                        icon = ImageVector.vectorResource(id = R.drawable.dollar),
                        flowColor = null
                    )
                    val absoluteChangeFormatted = (coin.priceUsd.value * (coin.changePercent24Hr.value / 100)).toDisplayableNumber()
                    val isPositive = coin.changePercent24Hr.value > 0.0
                    val contentColor = if(isPositive){
                        if (isSystemInDarkTheme()) Color.Green else greenBackground
                    }else{
                        MaterialTheme.colorScheme.error
                    }
                    InfoCard(
                        title = stringResource(id = R.string.change_last_24h),
                        formattedText = absoluteChangeFormatted.formatted,
                        icon = ImageVector.vectorResource(id = if(isPositive){ R.drawable.trending }else{ R.drawable.trending_down }),
                        flowColor = contentColor
                    )
                }
            }
        }
    }
}



@PreviewLightDark
@Composable
private fun CoinDetailScreenPreview(){

    val bg =  if(isSystemInDarkTheme()){
        backgroundDarkHighContrast
    }else{
        backgroundLightHighContrast
    }


    val viewModel = koinViewModel<CoinListViewModel>()


    BitPlusTheme {
        CoinDetailScreen(
            state = CoinListState(
                selectedCoin = previewCoin,
            ),
            coinUi = previewCoin,
            viewModel = viewModel,
            modifier = Modifier,
            onAction = {}
        )
    }
}