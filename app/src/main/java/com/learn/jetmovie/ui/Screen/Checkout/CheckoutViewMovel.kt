package com.learn.jetmovie.ui.Screen.Checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.jetmovie.data.MoviesRepository
import com.learn.jetmovie.ui.common.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CheckoutViewMovel(
    private val repository: MoviesRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<State<CheckoutState>> = MutableStateFlow(State.Loading)
    val uiState: StateFlow<State<CheckoutState>>
        get() = _uiState

    fun getAddedOrderRewards() {
        viewModelScope.launch {
            _uiState.value = State.Loading
            repository.getAddedBuyMoviesTickets()
                .collect { orderReward ->
                    val totalRequiredPoint =
                        orderReward.sumOf { it.movies.price * it.count }
                    _uiState.value = State.Success(CheckoutState(orderReward, totalRequiredPoint))
                }
        }
    }

    fun updateOrderReward(rewardId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateBuyMoviesTicket(rewardId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedOrderRewards()
                    }
                }
        }
    }
}