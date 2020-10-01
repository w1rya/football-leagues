package com.wiryatech.footballleagues.teams

import com.google.gson.Gson
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.api.SportsApi
import com.wiryatech.footballleagues.models.TeamResponse
import com.wiryatech.footballleagues.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class TeamsPresenter(
    private val view: TeamsView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    private lateinit var data: TeamResponse

    fun getTeamList(id: String) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            try {
                data = gson.fromJson(
                    apiRepository.doRequestAsync(SportsApi.getTeams(id)).await(),
                    TeamResponse::class.java
                )

                view.hideLoading()
                view.showTeamList(data.teams)

            } catch (e: UnknownHostException) {
                view.hideLoading()
                view.showNoConnection()
            }
        }
    }

    fun getDetail(id: String) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            try {
                data = gson.fromJson(
                    apiRepository.doRequestAsync(SportsApi.getTeamDetail(id)).await(),
                    TeamResponse::class.java
                )

                view.hideLoading()
                view.showTeamList(data.teams)

            } catch (e: UnknownHostException) {
                view.hideLoading()
                view.showNoConnection()
            }
        }
    }

}