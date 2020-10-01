package com.wiryatech.footballleagues.detail

import com.google.gson.Gson
import com.wiryatech.footballleagues.TestContextProvider
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.models.League
import com.wiryatech.footballleagues.models.LeagueResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class LeagueDetailPresenterTest {

    private lateinit var presenter: LeagueDetailPresenter

    @Mock
    private lateinit var view: LeagueDetailView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var repository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = LeagueDetailPresenter(view, repository, gson, TestContextProvider())
    }

    @Test
    fun getLeagueDetail() {
        val leagueDetail = mutableListOf<League>()
        val response = LeagueResponse(leagueDetail)
        val id = "4328"

        runBlocking {
            Mockito.`when`(repository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson(
                    "",
                    LeagueResponse::class.java
                )
            ).thenReturn(response)

            presenter.getLeagueDetail(id)

            verify(view).showLeagueDetail(leagueDetail)
        }
    }
}