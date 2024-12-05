package com.app.bitplus.crypto.presentation.coin_list



import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.bitplus.R
import com.app.bitplus.crypto.presentation.CoinListState
import com.app.bitplus.crypto.presentation.WindowInfo
import com.app.bitplus.crypto.presentation.coin_list.components.CoinListItem
import com.app.bitplus.crypto.presentation.coin_list.components.previewCoin
import com.app.bitplus.crypto.presentation.rememberWindowInfo
import com.app.bitplus.ui.theme.BitPlusTheme
import com.app.bitplus.ui.theme.backgroundDarkHighContrast
import com.app.bitplus.ui.theme.backgroundLightHighContrast
import com.app.bitplus.ui.theme.onPrimaryContainerDark
import com.app.bitplus.ui.theme.onPrimaryContainerLight
import com.app.bitplus.ui.theme.onTertiaryDark
import com.app.bitplus.ui.theme.onTertiaryLight
import org.koin.androidx.compose.koinViewModel



@Composable
fun CoinListScreen(
    modifier: Modifier = Modifier,
    state: CoinListState,
    viewModel: CoinListViewModel,
    onAction: (CoinListAction) -> Unit,
    navController: NavController
){


    val windowInfo = rememberWindowInfo()


    val bg =  if(isSystemInDarkTheme()){
        backgroundDarkHighContrast
    }else{
        backgroundLightHighContrast
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

    var searchQuery by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = searchQuery) {
        viewModel.loadCoins()
    }


    val searchedCoins = state.coins.filter { it.name.lowercase().contains(searchQuery.lowercase()) }
    if(windowInfo.screenWidth is WindowInfo.WindowType.Compact){
        Column(
            modifier = modifier.padding(20.dp)
                .background(bg)
                .fillMaxSize(),
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = "back",
                    tint = tertiaryOnContainer,
                    modifier = Modifier
                        .clickable { navController.popBackStack() }
                        .size(35.dp)
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "Coins",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Medium,
                    color = tertiaryOnContainer,
                )
            }
            Spacer(Modifier.height(30.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = searchQuery,
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "search")
                },
                onValueChange = { query->
                    searchQuery = query
                },
                placeholder = {
                    Text(text = "Search", color = Color.DarkGray )
                },
                maxLines = 1,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedPlaceholderColor = Color.Green,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = primaryOnContainer
                )
            )
            if(state.isLoading){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(bg),
                    contentAlignment = Alignment.Center){
                    CircularProgressIndicator(
                        color = primaryOnContainer
                    )
                }
            }else{
                LazyColumn (
                    modifier = Modifier.padding(top = 20.dp).weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ){
                    items(
                        searchedCoins.ifEmpty {
                            state.coins
                        }
                    ){ coinUi ->
                        CoinListItem(
                            coinUi = coinUi,
                            onClick = { onAction(CoinListAction.OnCoinClick(coinUi)) },
                            modifier = Modifier.padding(10.dp).fillMaxWidth()
                        )
                    }
                }
            }
        }
    }else{
        val halfSize = state.coins.size / 2
        val firstHalf = state.coins.subList(0, halfSize)
        val secondHalf = state.coins.subList(halfSize, state.coins.size)

        Column(
            modifier = modifier.padding(start = 40.dp)
                .background(bg)
                .fillMaxSize(),
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = "back",
                    tint = tertiaryOnContainer,
                    modifier = Modifier
                        .clickable { navController.popBackStack() }
                        .size(35.dp)
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "Coins",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Medium,
                    color = tertiaryOnContainer,
                )
            }
            Spacer(Modifier.height(30.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = searchQuery,
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "search")
                },
                onValueChange = { query->
                    searchQuery = query
                },
                placeholder = {
                    Text(text = "Search", color = Color.DarkGray )
                },
                maxLines = 1,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedPlaceholderColor = Color.Green,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = primaryOnContainer
                )
            )
            if(state.isLoading){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(bg),
                    contentAlignment = Alignment.Center){
                    CircularProgressIndicator(
                        color = primaryOnContainer
                    )
                }
            }else{
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LazyColumn (
                        modifier = Modifier.weight(1f).padding(top = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ){
                        items(
                            if(searchedCoins.isNotEmpty()){
                                firstHalf.filter { it.name.contains(searchQuery) }
                            }else{
                                firstHalf
                            }
                        ){ coinUi ->
                            CoinListItem(
                                coinUi = coinUi,
                                onClick = { onAction(CoinListAction.OnCoinClick(coinUi)) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                    LazyColumn (
                        modifier = Modifier.weight(1f).fillMaxWidth().padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ){
                        items(
                            if(searchedCoins.isNotEmpty()){
                                secondHalf.filter { it.name.contains(searchQuery) }
                            }else{
                                secondHalf
                            }
                        ){ coinUi ->
                            CoinListItem(
                                coinUi = coinUi,
                                onClick = { onAction(CoinListAction.OnCoinClick(coinUi)) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}

//onAction(CoinListAction.OnCoinClick(coinUi))


@PreviewLightDark
@Composable
private fun CoinListScreenPreview(){

    val bg =  if(isSystemInDarkTheme()){
        backgroundDarkHighContrast
    }else{
        backgroundLightHighContrast
    }

    val navController = NavController(LocalContext.current)

    val viewModel = koinViewModel<CoinListViewModel>()
    BitPlusTheme {
        CoinListScreen(
            state = CoinListState(
                coins = (1..100).map {
                    previewCoin.copy(id = it.toString())
                }
            ),
            modifier = Modifier.background(bg),
            onAction = {},
            viewModel = viewModel,
            navController = navController

        )
    }
}