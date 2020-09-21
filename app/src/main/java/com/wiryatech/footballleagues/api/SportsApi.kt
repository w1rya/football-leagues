package com.wiryatech.footballleagues.api

import android.net.Uri
import com.wiryatech.footballleagues.BuildConfig

object SportsApi {

    fun getLeagueDetail(id: String): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("lookupleague.php")
            .appendQueryParameter("id", id)
            .build()
            .toString()
    }

}