package com.wiryatech.footballleagues.matches

import com.wiryatech.footballleagues.models.DetailMatch

interface MatchDetailView {
    fun showLoading()
    fun hideLoading()
    fun showMatchDetail(data: List<DetailMatch>)
    fun showError(code: Int)
    fun showSnackBar(message: String)
}