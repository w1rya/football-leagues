package com.wiryatech.footballleagues.matches

import com.wiryatech.footballleagues.models.Match

interface MatchListView {
    fun showLoading()
    fun hideLoading()
    fun showPrevMatch(data: List<Match>)
    fun showNextMatch(data: List<Match>)
    fun showError(code: Int)
}