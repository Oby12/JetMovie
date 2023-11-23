package com.learn.jetmovie.data

import com.learn.jetmovie.model.BuyMoviesTicket
import com.learn.jetmovie.model.FakeMoviesData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class MoviesRepository {

    private val buyMoviesTicket = mutableListOf<BuyMoviesTicket>()

    init {
        if (buyMoviesTicket.isEmpty()) {
            FakeMoviesData.listMovies.forEach {
                buyMoviesTicket.add(BuyMoviesTicket(it, 0))
            }
        }
    }

    fun getAllMovies(): Flow<List<BuyMoviesTicket>> {
        return flowOf(buyMoviesTicket)
    }

    fun getBuyMoviesTicketById(moviesId: Long): BuyMoviesTicket {
        return buyMoviesTicket.first {
            it.movies.id == moviesId
        }
    }

    fun updateBuyMoviesTicket(moviesId: Long, newCountValue: Int): Flow<Boolean> {
        val index = buyMoviesTicket.indexOfFirst { it.movies.id == moviesId }
        val result = if (index >= 0) {
            val orderReward = buyMoviesTicket[index]
            buyMoviesTicket[index] =
                orderReward.copy(movies = orderReward.movies, count = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedBuyMoviesTickets(): Flow<List<BuyMoviesTicket>> {
        return getAllMovies()
            .map { buyMoviesTicket ->
                buyMoviesTicket.filter { orderReward ->
                    orderReward.count != 0
                }
            }
    }

    //fitur pencarian
    fun searchMovies(query : String) : Flow<List<BuyMoviesTicket>>{
        return getAllMovies()
            .map { result ->
                result.filter {
                    it.movies.title.contains(query, ignoreCase = true)
                }
            }
    }

    companion object {
        @Volatile
        private var instance: MoviesRepository? = null

        fun getInstance(): MoviesRepository =
            instance ?: synchronized(this) {
                MoviesRepository().apply {
                    instance = this
                }
            }
    }
}