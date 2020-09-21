package com.wiryatech.footballleagues.detail

import android.util.Log
import com.google.gson.Gson
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.api.SportsApi
import com.wiryatech.footballleagues.models.LeagueResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class LeagueDetailPresenter(
    private val view: LeagueDetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    fun getLeagueDetail(id: String) {
        view.showLoading()
        Log.d("Presenter", id)

        doAsync {
            val data = gson.fromJson(
                apiRepository.doRequest(SportsApi.getLeagueDetail(id)),
                LeagueResponse::class.java
            )

            Log.d("Presenter", "doAsync Finish")

            uiThread {
                view.hideLoading()
                view.showLeagueDetail(data.leagues)
            }
        }
    }

}