package com.wiryatech.footballleagues.models

data class Team(
    val idTeam: String,
    val strTeam: String,
    val strDescriptionEN: String,
    val strTeamBadge: String,
    val intFormedYear: Int,
    val strSport: String,
    val strLeague: String,
    val strGender: String,
    val strCountry: String,
    val strStadium: String,

    // image
    val strTeamJersey: String? = null,
    val strTeamBanner: String? = null,

    // stadium
    val strStadiumThumb: String? = null,
    val strStadiumLocation: String? = null,
    val intStadiumCapacity: Int? = null

)