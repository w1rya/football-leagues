package com.wiryatech.footballleagues.favorite

import com.wiryatech.footballleagues.db.FavoriteMatch
import com.wiryatech.footballleagues.db.FavoriteTeams

interface FavoriteView {
    fun showLoading()
    fun hideLoading()
    fun showFavMatch(data: List<FavoriteMatch>)
    fun showFavTeams(data: List<FavoriteTeams>)
    fun showNoData()
}