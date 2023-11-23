package com.learn.jetmovie.ui.Screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.learn.jetmovie.di.Injection
import com.learn.jetmovie.model.BuyMoviesTicket
import com.learn.jetmovie.ui.ViewModelFactory
import com.learn.jetmovie.ui.components.MovieItem

import com.learn.jetmovie.ui.common.State
import com.learn.jetmovie.ui.components.SearchBarProses

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    //parameter unutk detail
    navigateToDetail : (Long) -> Unit,

) {

    val searchState by viewModel.searchState

    viewModel.state.collectAsState(initial = State.Loading).value.let { state ->
        when (state){
            is State.Loading -> {
                viewModel.getAllMovies()
            }
            is State.Success -> {
                HomeContent(
                    buyMoviesTicket = state.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                    query = searchState.query,
                    onQueryChange = viewModel::onQueryChange
                )
            }
            is State.Error -> {}
        }
    }
}

//menmapilkan list item di home screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    buyMoviesTicket: List<BuyMoviesTicket>,
    modifier: Modifier = Modifier,
    //untuk detail
    navigateToDetail: (Long) -> Unit,
    query: String,
    onQueryChange: (String) -> Unit,

) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {

        SearchBarProses(
            query = query ,
            onQueryChange = onQueryChange,
            modifier = Modifier.background(MaterialTheme.colorScheme.primary)
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier.testTag("MovieList")
        ) {
            items(buyMoviesTicket) { data ->
                MovieItem(
                    title = data.movies.title,
                    photoUrl = data.movies.photoUrl,
                    price = data.movies.price,
                    modifier = Modifier.clickable {
                        navigateToDetail(data.movies.id)
                    }
                )
            }
        }
    }

}