package com.wiryatech.footballleagues.detail

import android.util.Log
import com.google.gson.Gson
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.api.SportsApi
import com.wiryatech.footballleagues.models.LeagueResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.UnknownHostException

class LeagueDetailPresenter(
    private val view: LeagueDetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {

    private lateinit var data: LeagueResponse

    fun getLeagueDetail(id: String) {
        view.showLoading()

        doAsync {
            try {
                data = gson.fromJson(
                    apiRepository.doRequest(SportsApi.getLeagueDetail(id)),
                    LeagueResponse::class.java
                )
            } catch (e: UnknownHostException) {
                Log.d("Presenter Connection", "$e")
                view.showNoConnection()
                Log.d("Presenter", "getLeagueDetail: No Connection")
            }

            uiThread {
                try {
                    view.hideLoading()
                    view.showLeagueDetail(data.leagues)
                } catch (e: UninitializedPropertyAccessException) {
                    Log.d("Presenter", "$e")
                    view.hideLoading()
                }
            }
        }
    }

}