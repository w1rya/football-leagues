package com.wiryatech.footballleagues.matches

import com.google.gson.Gson
import com.wiryatech.footballleagues.TestContextProvider
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.models.DetailMatch
import com.wiryatech.footballleagues.models.DetailMatchResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MatchDetailPresenterTest {

    private lateinit var presenter: MatchDetailPresenter

    private lateinit var testContextProvider: TestContextProvider

    @Mock
    private lateinit var view: MatchDetailView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var repository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        testContextProvider = TestContextProvider()
        presenter = MatchDetailPresenter(view, repository, gson, testContextProvider)
    }

    @Test
    fun getMatchDetail() {
        val matchDetail = mutableListOf<DetailMatch>()
        val response = DetailMatchResponse(matchDetail)
        val id = "1019528"

        runBlocking {
            Mockito.`when`(repository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson(
                    "",
                    DetailMatchResponse::class.java
                )
            ).thenReturn(response)

            presenter.getMatchDetail(id)

            Mockito.verify(view).showMatchDetail(matchDetail)
        }
    }
}