package com.learn.jetmovie.model

data class Movies(
    val id : Long,
    val title : String,
    val photoUrl : String,
    val sinopsis : String,
    val price : Int,
)