package com.learn.jetmovie.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.learn.jetmovie.data.MoviesRepository
import com.learn.jetmovie.ui.Screen.Checkout.CheckoutViewMovel
import com.learn.jetmovie.ui.Screen.detail.DetailViewModel
import com.learn.jetmovie.ui.Screen.home.HomeViewModel

class ViewModelFactory(private val repository: MoviesRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CheckoutViewMovel::class.java)) {
            return CheckoutViewMovel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}