package com.wiryatech.footballleagues.models

data class Standing(
    val name: String,
    val played: Int? = 0,
    val goalsdifference: Int? = 0,
    val win: Int? = 0,
    val draw: Int? = 0,
    val loss: Int? = 0,
    val total: Int? = 0
)