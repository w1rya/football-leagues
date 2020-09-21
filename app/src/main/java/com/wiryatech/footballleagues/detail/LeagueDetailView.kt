package com.wiryatech.footballleagues.detail

import com.wiryatech.footballleagues.models.League

interface LeagueDetailView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueDetail(data: List<League>)
}