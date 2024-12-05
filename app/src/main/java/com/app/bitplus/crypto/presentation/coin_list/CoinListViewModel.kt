package com.app.bitplus.crypto.presentation.coin_list


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bitplus.core.domain.util.onError
import com.app.bitplus.core.domain.util.onSuccess
import com.app.bitplus.crypto.domain.CoinDataSource
import com.app.bitplus.crypto.presentation.CoinHistoryState
import com.app.bitplus.crypto.presentation.CoinListState
import com.app.bitplus.crypto.presentation.CoinSharedPreference
import com.app.bitplus.crypto.presentation.models.toCoinHistoryUi
import com.app.bitplus.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch




class CoinListViewModel(
    private val coinDataSource: CoinDataSource,
    private val context: Context
): ViewModel() {
    private val _state = MutableStateFlow(CoinListState())
    private val _historyState = MutableStateFlow(CoinHistoryState())

    val state = _state.onStart { loadCoins() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CoinListState()
        )

    val historyState = _historyState.onStart { loadCoinsHistory(context) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CoinHistoryState()
        )

    private val _events = Channel<CoinListEvent>()
    val events = _events.receiveAsFlow()


    fun onAction(action: CoinListAction){
        when(action){
            is CoinListAction.OnCoinClick ->{
                _state.update { it.copy(
                    selectedCoin = action.coinUi
                ) }
            }
        }
    }



     fun loadCoins(){
        viewModelScope.launch {
            _state.update { it.copy(
                isLoading = true
            ) }

            coinDataSource.getCoins()
                .onSuccess { coins->
                    _state.update { it.copy(
                        isLoading = false,
                        coins = coins.map { it.toCoinUi() }
                    ) }
                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false) }
                    _events.send(CoinListEvent.Error(error))
                }

        }
    }


     fun loadCoinsHistory(context: Context){
        viewModelScope.launch {
            _historyState.update { it.copy(
                isLoading = true
            ) }

            val coinName = CoinSharedPreference.getCoinName(context).toString().replace(" ", "-")

            coinDataSource.getCoinsHistory(coinName)
                .onSuccess { coins ->
                        _historyState.update { it.copy(
                            isLoading = false,
                            coins = coins.map { it.toCoinHistoryUi() }
                        ) }

                }
                .onError { error ->
                        _historyState.update { it.copy(isLoading = false) }
                        _events.send(CoinListEvent.Error(error))

                }
        }
    }

}
