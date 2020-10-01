package com.wiryatech.footballleagues.standing

import com.wiryatech.footballleagues.models.Standing

interface StandingView {
    fun showLoading()
    fun hideLoading()
    fun showStanding(data: List<Standing>)
    fun showNoConnection()
}