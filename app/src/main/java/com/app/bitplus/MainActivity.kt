package com.app.bitplus



import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.bitplus.core.presentation.util.ObserveAsEvents
import com.app.bitplus.core.presentation.util.toString
import com.app.bitplus.crypto.presentation.Screen
import com.app.bitplus.crypto.presentation.coin_detail.CoinDetailScreen
import com.app.bitplus.crypto.presentation.coin_list.CoinListEvent
import com.app.bitplus.crypto.presentation.coin_list.CoinListScreen
import com.app.bitplus.crypto.presentation.coin_list.CoinListViewModel
import com.app.bitplus.crypto.presentation.mainScreen.MainScreen
import com.app.bitplus.ui.theme.BitPlusTheme
import com.app.bitplus.ui.theme.backgroundDarkHighContrast
import com.app.bitplus.ui.theme.backgroundLightHighContrast
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.compose.koinViewModel




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BitPlusTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val bg =  if(isSystemInDarkTheme()){
                        backgroundDarkHighContrast
                    }else{
                        backgroundLightHighContrast
                    }


                    val viewModel = koinViewModel<CoinListViewModel>()
                    val state by viewModel.state.collectAsStateWithLifecycle()
                    val historyState by viewModel.historyState.collectAsStateWithLifecycle()
                    val context = LocalContext.current
                    val navController = rememberNavController()

                    SetBarColor(color = bg)
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route
                    ){
                        composable(route = Screen.Home.route){
                            MainScreen(
                                modifier = Modifier.padding(innerPadding),
                                historyState = historyState,
                                navController = navController
                            )
                        }
                        composable(route = Screen.CoinList.route){
                            if(state.selectedCoin != null){
                                CoinDetailScreen(
                                    state = state,
                                    coinUi = state.selectedCoin!!,
                                    viewModel = viewModel,
                                    modifier = Modifier.padding(innerPadding),
                                    onAction = viewModel::onAction
                                )
                            }else{
                                CoinListScreen(
                                    state = state,
                                    modifier = Modifier.padding(innerPadding),
                                    onAction = viewModel::onAction,
                                    viewModel = viewModel,
                                    navController = navController
                                )
                            }
                        }
                    }
                    ObserveAsEvents(events = viewModel.events) { event ->
                        when(event){
                            is CoinListEvent.Error ->{
                                Toast.makeText(
                                    context,
                                    event.error.toString(context),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun SetBarColor(color: Color) {
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setSystemBarsColor(
                color = color
            )
        }
    }


}
