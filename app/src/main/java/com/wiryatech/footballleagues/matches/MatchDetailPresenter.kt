package com.wiryatech.footballleagues.matches

import android.util.Log
import com.google.gson.Gson
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.api.SportsApi
import com.wiryatech.footballleagues.models.DetailMatchResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.UnknownHostException

class MatchDetailPresenter(
    private val view: MatchDetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {

    private lateinit var data: DetailMatchResponse

    fun getMatchDetail(id: String) {
        view.showLoading()

        doAsync {
            try {
                data = gson.fromJson(
                    apiRepository.doRequest(SportsApi.getMatchDetail(id)),
                    DetailMatchResponse::class.java
                )
            } catch (e: UnknownHostException) {
                Log.d("Presenter Connection", "$e")
                view.hideLoading()
            }

            uiThread {
                try {
                    view.hideLoading()
                    view.showMatchDetail(data.events)
                } catch (e: UninitializedPropertyAccessException) {
                    Log.d("Presenter", "$e")
                    view.hideLoading()
                }
            }
        }
    }

}