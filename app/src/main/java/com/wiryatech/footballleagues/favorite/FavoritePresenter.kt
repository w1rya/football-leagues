package com.wiryatech.footballleagues.favorite

import android.content.Context
import com.wiryatech.footballleagues.db.FavoriteMatch
import com.wiryatech.footballleagues.db.FavoriteTeams
import com.wiryatech.footballleagues.db.db
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoritePresenter(private val view: FavoriteView) {

    fun showFavoriteMatch(context: Context) {
        view.showLoading()

        context.db.use {
            val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
            val favorite = result.parseList(classParser<FavoriteMatch>())

            if (favorite.isNullOrEmpty()) {
                view.hideLoading()
                view.showNoData()
            } else {
                view.hideLoading()
                view.showFavMatch(favorite)
            }
        }
    }

    fun showFavoriteTeams(context: Context) {
        view.showLoading()

        context.db.use {
            val result = select(FavoriteTeams.TABLE_FAVORITE_TEAM)
            val favorite = result.parseList(classParser<FavoriteTeams>())

            if (favorite.isNullOrEmpty()) {
                view.hideLoading()
                view.showNoData()
            } else {
                view.hideLoading()
                view.showFavTeams(favorite)
            }
        }
    }

}