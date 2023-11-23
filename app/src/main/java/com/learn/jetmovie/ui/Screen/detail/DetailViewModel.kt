package com.learn.jetmovie.ui.Screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.jetmovie.data.MoviesRepository
import com.learn.jetmovie.model.BuyMoviesTicket
import com.learn.jetmovie.model.Movies
import com.learn.jetmovie.ui.common.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: MoviesRepository) : ViewModel() {

    private val _state : MutableStateFlow<State<BuyMoviesTicket>> = MutableStateFlow(State.Loading)
    val state: StateFlow<State<BuyMoviesTicket>>
        get() = _state

    fun getMovieById(movieId : Long){
        viewModelScope.launch {
            _state.value = State.Loading
            _state.value = State.Success(repository.getBuyMoviesTicketById(movieId))
        }
    }

    fun addToCheckout(movie : Movies, count : Int){
        viewModelScope.launch {
            repository.updateBuyMoviesTicket(movie.id, count)
        }
    }
}