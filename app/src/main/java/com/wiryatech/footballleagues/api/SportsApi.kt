package com.wiryatech.footballleagues.api

import com.wiryatech.footballleagues.BuildConfig

object SportsApi {

    private const val API_V1 = "api/v1/json/"
    private const val LEAGUE_DETAIL = "/lookupleague.php?id="
    private const val MATCH_DETAIL = "/lookupevent.php?id="
    private const val PREVIOUS_MATCH = "/eventspastleague.php?id="
    private const val NEXT_MATCH = "/eventsnextleague.php?id="
    private const val STANDING = "/lookuptable.php?l="
    private const val TEAMS = "/lookup_all_teams.php?id="
    private const val TEAM_DETAIL = "/lookupteam.php?id="
    private const val SEARCH_MATCH = "/searchevents.php?e="
    private const val SEARCH_TEAM = "/searchteams.php?t="

    fun getLeagueDetail(id: String): String {
        return BuildConfig.BASE_URL + API_V1 + BuildConfig.TSDB_API_KEY + LEAGUE_DETAIL + id
    }

    fun getMatchDetail(id: String): String {
        return BuildConfig.BASE_URL + API_V1 + BuildConfig.TSDB_API_KEY + MATCH_DETAIL + id
    }

    fun getPrevMatch(id: String): String {
        return BuildConfig.BASE_URL + API_V1 + BuildConfig.TSDB_API_KEY + PREVIOUS_MATCH + id
    }

    fun getNextMatch(id: String): String {
        return BuildConfig.BASE_URL + API_V1 + BuildConfig.TSDB_API_KEY + NEXT_MATCH + id
    }

    fun getStanding(l: String): String {
        return BuildConfig.BASE_URL + API_V1 + BuildConfig.TSDB_API_KEY + STANDING + l
    }

    fun getTeams(id: String): String {
        return BuildConfig.BASE_URL + API_V1 + BuildConfig.TSDB_API_KEY + TEAMS + id
    }

    fun getTeamDetail(id: String): String {
        return BuildConfig.BASE_URL + API_V1 + BuildConfig.TSDB_API_KEY + TEAM_DETAIL + id
    }

    fun searchMatch(e: String): String {
        return BuildConfig.BASE_URL + API_V1 + BuildConfig.TSDB_API_KEY + SEARCH_MATCH + e
    }

    fun searchTeam(t: String): String {
        return BuildConfig.BASE_URL + API_V1 + BuildConfig.TSDB_API_KEY + SEARCH_TEAM + t
    }

}