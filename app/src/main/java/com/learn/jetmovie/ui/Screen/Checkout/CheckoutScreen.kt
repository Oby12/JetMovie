package com.learn.jetmovie.ui.Screen.Checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.learn.jetmovie.R
import com.learn.jetmovie.di.Injection
import com.learn.jetmovie.ui.ViewModelFactory
import com.learn.jetmovie.ui.common.State
import com.learn.jetmovie.ui.components.BuyBotton
import com.learn.jetmovie.ui.components.CheckoutItem

@Composable
fun CheckoutScreen(
    viewModel: CheckoutViewMovel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    onOrderButtonCLicked : (String) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = State.Loading).value.let { uiState ->
        when (uiState) {
            is State.Loading -> {
                viewModel.getAddedOrderRewards()
            }
            is State.Success -> {
                CartContent(
                    uiState.data,
                    onProductCountChanged = {rewardId, count ->
                        viewModel.updateOrderReward(rewardId,count)
                    },
                    onOrderButtonCLicked = onOrderButtonCLicked
                )
            }
            is State.Error -> {}
        }
    }

}

//membuat cart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContent(
    state: CheckoutState,
    onProductCountChanged : (id : Long, count: Int) -> Unit,
    onOrderButtonCLicked: (String) -> Unit,
    modifier: Modifier = Modifier
){
    val shareMessage = stringResource(
        R.string.share_message,
        state.orderMovie.count(),
        state.totalRequiredPoint
    )
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.menu_cart),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        )
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(weight = 1f)
        ){
            items(state.orderMovie, key ={it.movies.id }) { item ->
                CheckoutItem(
                    movieId = item.movies.id,
                    photoUrl = item.movies.photoUrl,
                    title = item.movies.title,
                    totalPoint = item.movies.price * item.count,
                    count = item.count,
                    onProductCountChanged = onProductCountChanged
                )
                Divider()
            }
        }
        BuyBotton(
            text = stringResource(R.string.total_order, state.totalRequiredPoint),
            enabled = state.orderMovie.isNotEmpty(),
            onClick = {
                onOrderButtonCLicked(shareMessage)
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}