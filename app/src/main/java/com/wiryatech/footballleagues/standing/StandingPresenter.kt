package com.wiryatech.footballleagues.standing

import android.util.Log
import com.google.gson.Gson
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.api.SportsApi
import com.wiryatech.footballleagues.models.StandingResponse
import com.wiryatech.footballleagues.utils.Constants
import com.wiryatech.footballleagues.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class StandingPresenter(
    private val view: StandingView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    private lateinit var data: StandingResponse

    fun getStanding(id: String) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            try {
                data = gson.fromJson(
                    apiRepository.doRequestAsync(SportsApi.getStanding(id)).await(),
                    StandingResponse::class.java
                )
                view.hideLoading()
                view.showStanding(data.table)
            } catch (e: UnknownHostException) {
                Log.d("Presenter Connection", "$e")
                view.showError(Constants.NO_CONNECTION)
                Log.d("Presenter", "getLeagueDetail: No Connection")
            } catch (e: NullPointerException) {
                view.hideLoading()
                view.showError(Constants.STANDING_NULL)
            }
        }
    }

}