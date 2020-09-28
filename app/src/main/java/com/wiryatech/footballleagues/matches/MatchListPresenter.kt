package com.wiryatech.footballleagues.matches

import android.util.Log
import com.google.gson.Gson
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.api.SportsApi
import com.wiryatech.footballleagues.models.MatchResponse
import com.wiryatech.footballleagues.models.SearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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

        GlobalScope.launch(Dispatchers.Main) {
            try {
                data = gson.fromJson(
                    apiRepository.doRequestAsync(SportsApi.getPrevMatch(id)).await(),
                    MatchResponse::class.java
                )

                try {
                    view.hideLoading()
                    data.events?.let { event -> view.showMatchList(event) }
                } catch (e: UninitializedPropertyAccessException) {
                    Log.d("Presenter", "$e")
                    view.hideLoading()
                    view.showNoData()
                }
            } catch (e: UnknownHostException) {
                Log.d("Presenter Connection", "$e")
                view.hideLoading()
                view.showNoConnection()
            }
        }
    }

    fun getNextMatch(id: String) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main) {
            try {
                data = gson.fromJson(
                    apiRepository.doRequestAsync(SportsApi.getNextMatch(id)).await(),
                    MatchResponse::class.java
                )

                try {
                    view.hideLoading()
                    data.events?.let { event -> view.showMatchList(event) }
                } catch (e: Exception) {
                    Log.d("Presenter", "$e")
                    view.hideLoading()
                    view.showNoData()
                }
            } catch (e: UnknownHostException) {
                Log.d("Presenter Connection", "$e")
                view.hideLoading()
                view.showNoConnection()
                Log.d("Presenter", "MatchList: No Connection")
            }
        }
    }

    fun searchMatch(e: String) {
        view.showLoading()
        Log.d("Presenter", e)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                dataSearch = gson.fromJson(
                    apiRepository.doRequestAsync(SportsApi.searchMatch(e)).await(),
                    SearchResponse::class.java
                )

                try {
                    view.hideLoading()
                    view.showMatchList(dataSearch.event)
                    Log.d("Presenter", "showMatchList finish")
                } catch (e: Exception) {
                    Log.d("Presenter", "$e")
                    view.hideLoading()
                    view.showNoData()
                }
            } catch (e: UnknownHostException) {
                Log.d("Presenter Connection", "$e")
                view.hideLoading()
                view.showNoConnection()
            }
            Log.d("Presenter", "doAsync finish")
        }
    }

}