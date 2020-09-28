package com.wiryatech.footballleagues.matches

import com.wiryatech.footballleagues.models.Match

interface MatchListView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<Match>)
    fun showNoData()
    fun showNoConnection()
}