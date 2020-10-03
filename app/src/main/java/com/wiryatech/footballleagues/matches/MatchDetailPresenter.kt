package com.wiryatech.footballleagues.matches

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.google.gson.Gson
import com.wiryatech.footballleagues.R
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.api.SportsApi
import com.wiryatech.footballleagues.db.FavoriteMatch
import com.wiryatech.footballleagues.db.db
import com.wiryatech.footballleagues.models.DetailMatch
import com.wiryatech.footballleagues.models.DetailMatchResponse
import com.wiryatech.footballleagues.utils.Constants
import com.wiryatech.footballleagues.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import java.net.UnknownHostException

class MatchDetailPresenter(
    private val view: MatchDetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    private lateinit var data: DetailMatchResponse
    private var state = false

    fun getMatchDetail(id: String) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            try {
                data = gson.fromJson(
                    apiRepository.doRequestAsync(SportsApi.getMatchDetail(id)).await(),
                    DetailMatchResponse::class.java
                )

                try {
                    view.hideLoading()
                    view.showMatchDetail(data.events)
                } catch (e: UninitializedPropertyAccessException) {
                    view.hideLoading()
                    view.showError(Constants.ACTIVITY_NULL)
                }
            } catch (e: UnknownHostException) {
                Log.d("Presenter Connection", "$e")
                view.hideLoading()
                view.showError(Constants.NO_CONNECTION)
            }

        }
    }

    fun setFavState(context: Context, id: String): Boolean {
        try {
            context.db.use {
                val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
                    .whereArgs("(EVENT_ID = {id})", "id" to id)
                val favorite = result.parseList(classParser<FavoriteMatch>())
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
                    FavoriteMatch.TABLE_FAVORITE_MATCH,
                    FavoriteMatch.EVENT_ID to match.idEvent,
                    FavoriteMatch.EVENT_NAME to match.strEvent,
                    FavoriteMatch.EVENT_DATE to match.dateEvent,
                    FavoriteMatch.LEAGUE_NAME to match.strLeague
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
                    FavoriteMatch.TABLE_FAVORITE_MATCH,
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