package com.wiryatech.footballleagues.teams

import com.wiryatech.footballleagues.models.Team

interface TeamsView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
    fun showNoConnection()
}