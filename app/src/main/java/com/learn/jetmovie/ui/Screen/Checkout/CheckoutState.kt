package com.learn.jetmovie.ui.Screen.Checkout

import com.learn.jetmovie.model.BuyMoviesTicket

data class CheckoutState(
    val orderMovie: List<BuyMoviesTicket>,
    val totalRequiredPoint: Int
)