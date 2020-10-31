package com.wiryatech.footballleagues.search

import com.google.gson.Gson
import com.wiryatech.footballleagues.TestContextProvider
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.models.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchPresenterTest {

    private val query = "Arsenal"
    private lateinit var searchPresenter: SearchPresenter

    private var matches = mutableListOf<Match>()
    private var matchResponse = SearchMatchResponse(matches)

    private var teams = mutableListOf<Team>()
    private var teamResponse = TeamResponse(teams)

    @Mock
    private lateinit var view: SearchesView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var repository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        searchPresenter = SearchPresenter(view, repository, gson, TestContextProvider())
    }

    @Test
    fun search() {
        runBlocking {
            Mockito.`when`(repository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    SearchMatchResponse::class.java
                )
            ).thenReturn(matchResponse)

            Mockito.`when`(
                gson.fromJson(
                    "",
                    TeamResponse::class.java
                )
            ).thenReturn(teamResponse)

            searchPresenter.search(query)

            Mockito.verify(view).showMatch(matches)
            Mockito.verify(view).showTeams(teams)
        }
    }

}