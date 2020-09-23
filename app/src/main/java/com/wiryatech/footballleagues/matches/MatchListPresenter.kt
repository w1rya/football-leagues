package com.wiryatech.footballleagues.matches

import android.util.Log
import com.google.gson.Gson
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.api.SportsApi
import com.wiryatech.footballleagues.models.MatchResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchListPresenter(
    private val view: MatchListView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {

    fun getPrevMatch(id: String) {
        view.showLoading()
        Log.d("Presenter", id)

        doAsync {
            val data = gson.fromJson(
                apiRepository.doRequest(SportsApi.getPrevMatch(id)),
                MatchResponse::class.java
            )
            Log.d("Presenter", "doAsync Finish")
            uiThread {
                view.hideLoading()
                view.showMatchList(data.events)
            }
        }
    }

}