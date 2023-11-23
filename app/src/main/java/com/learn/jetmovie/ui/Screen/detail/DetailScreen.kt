package com.learn.jetmovie.ui.Screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.learn.jetmovie.R
import com.learn.jetmovie.di.Injection
import com.learn.jetmovie.ui.ViewModelFactory
import com.learn.jetmovie.ui.common.State
import com.learn.jetmovie.ui.components.BuyBotton
import com.learn.jetmovie.ui.components.ProductCounter
import com.learn.jetmovie.ui.theme.JetMovieTheme

@Composable
fun DetailScreen(
    movieId : Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    //button navigasi back and ke checkout
    navigateBack : () -> Unit,
    navigateToCart: () -> Unit,
){

    viewModel.state.collectAsState(initial = State.Loading).value.let { state->
        when(state){
            is State.Loading -> {
                viewModel.getMovieById(movieId)
            }
            is State.Success -> {
                val data = state.data
                DetailContent(
                    title = data.movies.title,
                    photoUrl = data.movies.photoUrl ,
                    sinopsis = data.movies.sinopsis,
                    price = data.movies.price,
                    count = data.count,
                    onBackClick = navigateBack,
                    onAddToCart = {count ->
                        viewModel.addToCheckout(data.movies, count)
                        navigateToCart()
                    }
                )
            }

            else -> {}
        }

    }

}

//content
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    title: String,
    photoUrl : String,
    sinopsis : String,
    price : Int,
    count : Int,
    onBackClick: () -> Unit,
    onAddToCart: (count: Int) -> Unit,
    modifier: Modifier = Modifier,
){
    var totalPoint by rememberSaveable { mutableStateOf(0) }
    var orderCount by rememberSaveable { mutableStateOf(count) }

    //makeDesign
    Column(modifier = modifier) {
        //back and text DEtail top bar
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.detail_text) ,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                )
            },
            navigationIcon = {
                Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back_text),
                modifier = Modifier.padding(15.dp).clickable { onBackClick() }
                )

            }
        )
        //photo detail
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            //Top
            Box{
               AsyncImage(
                   model = photoUrl,
                   contentDescription = null,
                   contentScale = ContentScale.Crop,
                   modifier = modifier
                       .height(480.dp)
                       .fillMaxWidth()
                       .padding(20.dp)
                       .clip(RoundedCornerShape(17.dp))
                   )
            }
            //text title and price
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.title_text),
                    style =MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = modifier
                        .padding(vertical = 10.dp)

                )
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 30.sp,
                    ),
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                )

                Text(
                    text = stringResource(id = R.string.price_text),
                    style =MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = modifier
                        .padding(vertical = 10.dp)

                )
                Text(
                    text = stringResource(id = R.string.required_point, price),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = MaterialTheme.colorScheme.secondary,
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                )

                Text(
                    text = stringResource(id = R.string.sinopsis_text),
                    style =MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = modifier
                        .padding(vertical = 10.dp)

                )
                Text(
                    text = sinopsis,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start
                )

            }
        }

        //buttom count
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color.LightGray)
        )

        Row(
            modifier = Modifier.padding(16.dp)
        ) {

            ProductCounter(
                    orderId = 1,
                    orderCount = orderCount,
                    onProductIncreased = {orderCount++},
                    onProductDecreased = { if(orderCount > 0) orderCount-- }
                )
            Spacer(modifier = Modifier.width(30.dp))
            totalPoint = price * orderCount
            BuyBotton(
                text = stringResource(R.string.add_to_cart, totalPoint),
                enabled = orderCount > 0,
                onClick = {
                    onAddToCart(orderCount)
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

//preview
@Preview(showBackground = true, device = Devices.NEXUS_6P)
@Composable
fun DetailContentPreview(){
    JetMovieTheme {
        DetailContent(
            title = "bala bala",
            photoUrl = "",
            sinopsis = "heii bro saya bobby saputra",
            price = 120000,
            count = 1,
            onBackClick = {  },
            onAddToCart = {}
        )
    }
}
