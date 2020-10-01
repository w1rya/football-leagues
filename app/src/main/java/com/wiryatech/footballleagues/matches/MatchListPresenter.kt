package com.wiryatech.footballleagues.matches

import android.util.Log
import com.google.gson.Gson
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.api.SportsApi
import com.wiryatech.footballleagues.models.MatchResponse
import com.wiryatech.footballleagues.utils.Constants
import com.wiryatech.footballleagues.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class MatchListPresenter(
    private val view: MatchListView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    private lateinit var dataPrev: MatchResponse
    private lateinit var dataNext: MatchResponse

    fun getPrevMatch(id: String) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            try {
                dataPrev = gson.fromJson(
                    apiRepository.doRequestAsync(SportsApi.getPrevMatch(id)).await(),
                    MatchResponse::class.java
                )

                try {
                    view.hideLoading()
                    view.showPrevMatch(dataPrev.events)
                } catch (e: NullPointerException) {
                    view.hideLoading()
                    view.showError(Constants.PREV_MATCH_NULL)
                }
            } catch (e: UnknownHostException) {
                view.hideLoading()
                view.showError(Constants.NO_CONNECTION)
            }
        }
    }

    fun getNextMatch(id: String) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            try {
                dataNext = gson.fromJson(
                    apiRepository.doRequestAsync(SportsApi.getNextMatch(id)).await(),
                    MatchResponse::class.java
                )

                try {
                    view.hideLoading()
                    view.showNextMatch(dataNext.events)
                } catch (e: NullPointerException) {
                    view.hideLoading()
                    view.showError(Constants.NEXT_MATCH_NULL)
                    Log.d("MatchList", e.message.toString())
                }
            } catch (e: UnknownHostException) {
                view.hideLoading()
                view.showError(Constants.NO_CONNECTION)
            }
        }
    }

}