package com.wiryatech.footballleagues.models

data class Match(
    val idEvent: String,
    val strLeague: String,
    val strSeason: String,
    val strHomeTeam: String,
    val strAwayTeam: String,
    val intHomeScore: Int,
    val intAwayScore: Int,
    val strVenue: String
)