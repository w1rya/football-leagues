package com.wiryatech.footballleagues.search

import com.google.gson.Gson
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.api.SportsApi
import com.wiryatech.footballleagues.models.SearchMatchResponse
import com.wiryatech.footballleagues.models.TeamResponse
import com.wiryatech.footballleagues.utils.Constants
import com.wiryatech.footballleagues.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class SearchPresenter(
    private val view: SearchesView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    private lateinit var dataMatch: SearchMatchResponse
    private lateinit var dataTeams: TeamResponse

    fun searchMatch(query: String) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            try {
                dataMatch = gson.fromJson(
                    apiRepository.doRequestAsync(SportsApi.searchMatch(query)).await(),
                    SearchMatchResponse::class.java
                )

                dataTeams = gson.fromJson(
                    apiRepository.doRequestAsync(SportsApi.searchTeam(query)).await(),
                    TeamResponse::class.java
                )

                try {
                    view.hideLoading()
                    view.showMatch(dataMatch.event)
                    view.showTeams(dataTeams.teams)
                } catch (e: Exception) {
                    view.hideLoading()
                    view.showError(Constants.SEARCH_NULL)
                }
            } catch (e: UnknownHostException) {
                view.hideLoading()
                view.showError(Constants.NO_CONNECTION)
            }
        }
    }

}