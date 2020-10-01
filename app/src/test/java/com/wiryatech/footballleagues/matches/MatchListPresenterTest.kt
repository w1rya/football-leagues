package com.wiryatech.footballleagues.matches

import com.google.gson.Gson
import com.wiryatech.footballleagues.TestContextProvider
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.models.Match
import com.wiryatech.footballleagues.models.MatchResponse
import com.wiryatech.footballleagues.models.SearchMatchResponse
import com.wiryatech.footballleagues.search.SearchPresenter
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MatchListPresenterTest {

    private var matches = mutableListOf<Match>()
    private var matchResponse = MatchResponse(matches)
    private val searchResponse = SearchMatchResponse(matches)
    private val id = "4328"
    private val e = "Man United"

    private lateinit var matchPresenter: MatchListPresenter

    @Mock
    private lateinit var view: MatchListView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var repository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        matchPresenter = MatchListPresenter(view, repository, gson, TestContextProvider())
    }

    @Test
    fun getPrevMatch() {
        runBlocking {
            Mockito.`when`(repository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson(
                    "",
                    MatchResponse::class.java
                )
            ).thenReturn(matchResponse)

            matchPresenter.getPrevMatch(id)

            Mockito.verify(view).showPrevMatch(matches)
        }
    }

    @Test
    fun getNextMatch() {
        runBlocking {
            Mockito.`when`(repository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson(
                    "",
                    MatchResponse::class.java
                )
            ).thenReturn(matchResponse)

            matchPresenter.getNextMatch(id)

            Mockito.verify(view).showNextMatch(matches)
        }
    }

}