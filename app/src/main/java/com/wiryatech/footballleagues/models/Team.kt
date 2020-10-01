package com.wiryatech.footballleagues.models

data class Team(
    val idTeam: String,
    val strTeam: String,
    val strDescriptionEN: String,
    val intFormedYear: Int,
    val strSport: String,
    val strLeague: String,
    val strGender: String,
    val strCountry: String,

    // image
    val strTeamBadge: String,
    val strTeamJersey: String? = null,
    val strTeamBanner: String? = null,

    // stadium
    val strStadium: String,
    val strStadiumThumb: String? = null,
    val strStadiumLocation: String? = null,
    val intStadiumCapacity: Int? = null

)