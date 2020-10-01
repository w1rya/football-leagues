package com.wiryatech.footballleagues.teams

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.google.gson.Gson
import com.wiryatech.footballleagues.R
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.api.SportsApi
import com.wiryatech.footballleagues.db.FavoriteTeams
import com.wiryatech.footballleagues.db.db
import com.wiryatech.footballleagues.models.Team
import com.wiryatech.footballleagues.models.TeamResponse
import com.wiryatech.footballleagues.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import java.net.UnknownHostException

class TeamsPresenter(
    private val view: TeamsView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    private lateinit var data: TeamResponse
    private var state = false

    fun getTeamList(id: String) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            try {
                data = gson.fromJson(
                    apiRepository.doRequestAsync(SportsApi.getTeams(id)).await(),
                    TeamResponse::class.java
                )

                view.hideLoading()
                view.showTeamList(data.teams)

            } catch (e: UnknownHostException) {
                view.hideLoading()
                view.showNoConnection()
            }
        }
    }

    fun getDetail(id: String) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            try {
                data = gson.fromJson(
                    apiRepository.doRequestAsync(SportsApi.getTeamDetail(id)).await(),
                    TeamResponse::class.java
                )

                view.hideLoading()
                view.showTeamList(data.teams)

            } catch (e: UnknownHostException) {
                view.hideLoading()
                view.showNoConnection()
            }
        }
    }

    fun setFavState(context: Context, id: String): Boolean {
        try {
            context.db.use {
                val result = select(FavoriteTeams.TABLE_FAVORITE_TEAM)
                    .whereArgs("(TEAM_ID = {id})", "id" to id)
                val favorite = result.parseList(classParser<FavoriteTeams>())
                if (favorite.isNotEmpty()) state = true
                Log.d("Presenter", "setFavState: $state")
            }
        } catch (e: SQLiteConstraintException) {
            Log.d("setFavState: ", e.message.toString())
        }
        return state
    }

    fun addToFav(context: Context, team: Team) {
        try {
            context.db.use {
                insert(
                    FavoriteTeams.TABLE_FAVORITE_TEAM,
                    FavoriteTeams.TEAM_ID to team.idTeam,
                    FavoriteTeams.TEAM_NAME to team.strTeam,
                    FavoriteTeams.TEAM_BADGE to team.strTeamBadge,
                    FavoriteTeams.TEAM_DESC to team.strDescriptionEN
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
                    FavoriteTeams.TABLE_FAVORITE_TEAM,
                    "(TEAM_ID = {id})",
                    "id" to id
                )
            }
            view.showSnackBar(context.getString(R.string.del_fav))
        } catch (e: SQLiteConstraintException) {
            e.localizedMessage?.toString()?.let { view.showSnackBar(it) }
        }
    }

}