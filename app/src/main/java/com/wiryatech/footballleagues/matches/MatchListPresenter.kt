package com.wiryatech.footballleagues.matches

import android.util.Log
import com.google.gson.Gson
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.api.SportsApi
import com.wiryatech.footballleagues.models.MatchResponse
import com.wiryatech.footballleagues.models.SearchResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.UnknownHostException

class MatchListPresenter(
    private val view: MatchListView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {

    private lateinit var data: MatchResponse
    private lateinit var dataSearch: SearchResponse

    fun getPrevMatch(id: String) {
        view.showLoading()

        doAsync {
            try {
                data = gson.fromJson(
                    apiRepository.doRequest(SportsApi.getPrevMatch(id)),
                    MatchResponse::class.java
                )
            } catch (e: UnknownHostException) {
                Log.d("Presenter Connection", "$e")
                view.hideLoading()
            }

            uiThread {
                try {
                    view.hideLoading()
                    data.events?.let { event -> view.showMatchList(event) }
                } catch (e: UninitializedPropertyAccessException) {
                    Log.d("Presenter", "$e")
                    view.hideLoading()
                    view.showNoData()
                }
            }
        }
    }

    fun getNextMatch(id: String) {
        view.showLoading()

        doAsync {
            try {
                data = gson.fromJson(
                    apiRepository.doRequest(SportsApi.getNextMatch(id)),
                    MatchResponse::class.java
                )
            } catch (e: UnknownHostException) {
                Log.d("Presenter Connection", "$e")
                view.hideLoading()
            }

            uiThread {
                try {
                    view.hideLoading()
                    data.events?.let { event -> view.showMatchList(event) }
                } catch (e: Exception) {
                    Log.d("Presenter", "$e")
                    view.hideLoading()
                    view.showNoData()
                }
            }
        }
    }

    fun searchMatch(e: String) {
        view.showLoading()
        Log.d("Presenter", e)

        doAsync {
            try {
                dataSearch = gson.fromJson(
                    apiRepository.doRequest(SportsApi.searchMatch(e)),
                    SearchResponse::class.java
                )
            } catch (e: UnknownHostException) {
                Log.d("Presenter Connection", "$e")
                view.hideLoading()
            }
            Log.d("Presenter", "doAsync finish")

            uiThread {
                try {
                    view.hideLoading()
                    view.showMatchList(dataSearch.event)
                    Log.d("Presenter", "showMatchList finish")
                } catch (e: Exception) {
                    Log.d("Presenter", "$e")
                    view.hideLoading()
                    view.showNoData()
                }
            }
        }
    }

}