package com.wiryatech.footballleagues.detail

import android.util.Log
import com.google.gson.Gson
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.api.SportsApi
import com.wiryatech.footballleagues.models.LeagueResponse
import com.wiryatech.footballleagues.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class LeagueDetailPresenter(
    private val view: LeagueDetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    private lateinit var data: LeagueResponse

    fun getLeagueDetail(id: String) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            try {
                data = gson.fromJson(
                    apiRepository.doRequestAsync(SportsApi.getLeagueDetail(id)).await(),
                    LeagueResponse::class.java
                )

                try {
                    view.hideLoading()
                    view.showLeagueDetail(data.leagues)
                } catch (e: UninitializedPropertyAccessException) {
                    Log.d("Presenter", "$e")
                    view.hideLoading()
                }
            } catch (e: UnknownHostException) {
                Log.d("Presenter Connection", "$e")
                view.showNoConnection()
                Log.d("Presenter", "getLeagueDetail: No Connection")
            }
        }
    }

}