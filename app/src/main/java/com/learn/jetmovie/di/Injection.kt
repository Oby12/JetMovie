package com.learn.jetmovie.di

import com.learn.jetmovie.data.MoviesRepository

object Injection {
    fun provideRepository(): MoviesRepository {
        return MoviesRepository.getInstance()
    }
}