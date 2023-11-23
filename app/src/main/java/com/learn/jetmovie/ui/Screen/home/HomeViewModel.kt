package com.learn.jetmovie.ui.Screen.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.jetmovie.data.MoviesRepository
import com.learn.jetmovie.model.BuyMoviesTicket
import com.learn.jetmovie.ui.common.SearchState
import com.learn.jetmovie.ui.common.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository : MoviesRepository) : ViewModel() {

    private val _state : MutableStateFlow<State<List<BuyMoviesTicket>>> = MutableStateFlow(State.Loading)
    val state: StateFlow<State<List<BuyMoviesTicket>>>
        get() = _state

    //mendapatkan list film
    fun getAllMovies(){
        viewModelScope.launch {
            repository.getAllMovies()
                .catch {
                    _state.value = State.Error(it.message.toString())
                }
                .collect { buyTicketMovies ->
                    _state.value = State.Success(buyTicketMovies)
                }
        }
    }

    //membuat fungsi perncaharian
    private val _searchState = mutableStateOf(SearchState())
    val searchState: androidx.compose.runtime.State<SearchState> = _searchState

    private fun searchMovies(query : String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.searchMovies(query)
                .catch { _state.value = State.Error(it.message.toString()) }
                .collect { _state.value = State.Success(it) }
        }
    }

    fun onQueryChange(query : String){
        _searchState.value = _searchState.value.copy(query = query)
        searchMovies(query)
    }
}