package com.wiryatech.footballleagues.db

data class FavoriteTeams(
    val id: Long?, val teamId: String?, val teamName: String?, val teamBadge: String?, val teamDesc: String?
) {
    companion object {
        const val TABLE_FAVORITE_TEAM: String = "TABLE_FAVORITE_TEAM"
        const val ID: String = "ID_"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val TEAM_BADGE: String = "TEAM_BADGE"
        const val TEAM_DESC: String = "TEAM_DESC"
    }
}