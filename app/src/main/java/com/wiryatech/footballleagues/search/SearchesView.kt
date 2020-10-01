package com.wiryatech.footballleagues.search

import com.wiryatech.footballleagues.models.Match
import com.wiryatech.footballleagues.models.Team

interface SearchesView {
    fun showLoading()
    fun hideLoading()
    fun showMatch(data: List<Match>)
    fun showTeams(data: List<Team>)
    fun showError(code: Int)
}