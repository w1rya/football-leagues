package com.wiryatech.footballleagues.matches

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.google.gson.Gson
import com.wiryatech.footballleagues.R
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.api.SportsApi
import com.wiryatech.footballleagues.db.Favorite
import com.wiryatech.footballleagues.db.db
import com.wiryatech.footballleagues.models.DetailMatch
import com.wiryatech.footballleagues.models.DetailMatchResponse
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.UnknownHostException

class MatchDetailPresenter(
    private val view: MatchDetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {

    private lateinit var data: DetailMatchResponse
    private var state = false

    fun getMatchDetail(id: String) {
        view.showLoading()

        doAsync {
            try {
                data = gson.fromJson(
                    apiRepository.doRequest(SportsApi.getMatchDetail(id)),
                    DetailMatchResponse::class.java
                )
            } catch (e: UnknownHostException) {
                Log.d("Presenter Connection", "$e")
                view.hideLoading()
            }

            uiThread {
                try {
                    view.hideLoading()
                    view.showMatchDetail(data.events)
                } catch (e: UninitializedPropertyAccessException) {
                    Log.d("Presenter", "$e")
                    view.hideLoading()
                }
            }
        }
    }

    fun setFavState(context: Context, id: String): Boolean {
        try {
            context.db.use {
                val result = select(Favorite.TABLE_FAVORITE)
                    .whereArgs("(EVENT_ID = {id})", "id" to id)
                val favorite = result.parseList(classParser<Favorite>())
                if (favorite.isNotEmpty()) state = true
            }
        } catch (e: SQLiteConstraintException) {
            Log.d("setFavState: ", e.message.toString())
        }
        return state
    }

    fun addToFav(context: Context, match: DetailMatch) {
        try {
            context.db.use {
                insert(
                    Favorite.TABLE_FAVORITE,
                    Favorite.EVENT_ID to match.idEvent,
                    Favorite.EVENT_NAME to match.strEvent,
                    Favorite.EVENT_DATE to match.dateEvent,
                    Favorite.LEAGUE_NAME to match.strLeague
                )
            }
            view.showSnackBar(context.getString(R.string.add_fav))
        } catch (e: SQLiteConstraintException) {
            e.localizedMessage?.toString()?.let { view.showSnackBar(it) }
        }
    }

    fun delFromFav(context: Context, id: String) {
        try {
            context.db.use {
                delete(
                    Favorite.TABLE_FAVORITE,
                    "(EVENT_ID = {id})",
                    "id" to id
                )
            }
            view.showSnackBar(context.getString(R.string.del_fav))
        } catch (e: SQLiteConstraintException) {
            e.localizedMessage?.toString()?.let { view.showSnackBar(it) }
        }
    }

}