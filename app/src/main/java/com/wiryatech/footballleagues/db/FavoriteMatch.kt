package com.wiryatech.footballleagues.db

data class FavoriteMatch(
    val id: Long?, val eventId: String?, val eventName: String?, val eventDate: String?, val leagueName: String?
) {
    companion object {
        const val TABLE_FAVORITE_MATCH: String = "TABLE_FAVORITE_MATCH"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val EVENT_NAME: String = "EVENT_NAME"
        const val EVENT_DATE: String = "EVENT_DATE"
        const val LEAGUE_NAME: String = "LEAGUE_NAME"
    }
}