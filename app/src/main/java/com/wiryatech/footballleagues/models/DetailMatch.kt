package com.wiryatech.footballleagues.models

data class DetailMatch(
    val idEvent: String,
    val strThumb: String? = null,
    val strEvent: String,
    val strLeague: String,
    val strSeason: String,
    val strHomeTeam: String,
    val strAwayTeam: String,
    val intHomeScore: Int? = null,
    val intAwayScore: Int? = null,
    val strHomeFormation: String? = null,
    val strHomeGoalDetails: String? = null,
    val strHomeRedCards: String? = null,
    val strHomeYellowCards: String? = null,
    val strAwayFormation: String? = null,
    val strAwayGoalDetails: String? = null,
    val strAwayRedCards: String? = null,
    val strAwayYellowCards: String ? = null,
    val dateEvent: String? = null,
    val strVenue: String? = null
)