package com.wiryatech.footballleagues.teams

import com.google.gson.Gson
import com.wiryatech.footballleagues.TestContextProvider
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.models.Team
import com.wiryatech.footballleagues.models.TeamResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TeamsPresenterTest {

    private val idLeague = "4328"
    private val idTeam = "133612"
    private var teams = mutableListOf<Team>()
    private var teamResponse = TeamResponse(teams)

    private lateinit var teamsPresenter: TeamsPresenter

    @Mock
    private lateinit var view: TeamsView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var repository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        teamsPresenter = TeamsPresenter(view, repository, gson, TestContextProvider())
    }

    @Test
    fun getTeamList() {
        runBlocking {
            Mockito.`when`(repository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson(
                    "",
                    TeamResponse::class.java
                )
            ).thenReturn(teamResponse)

            teamsPresenter.getTeamList(idLeague)

            Mockito.verify(view).showTeamList(teams)
        }
    }

    @Test
    fun getDetail() {
        runBlocking {
            Mockito.`when`(repository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson(
                    "",
                    TeamResponse::class.java
                )
            ).thenReturn(teamResponse)

            teamsPresenter.getDetail(idTeam)

            Mockito.verify(view).showTeamList(teams)
        }
    }
}