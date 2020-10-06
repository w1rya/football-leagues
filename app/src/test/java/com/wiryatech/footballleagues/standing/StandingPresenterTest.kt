package com.wiryatech.footballleagues.standing

import com.google.gson.Gson
import com.wiryatech.footballleagues.TestContextProvider
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.models.Standing
import com.wiryatech.footballleagues.models.StandingResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class StandingPresenterTest {

    private val id = "4328"
    private var standing = mutableListOf<Standing>()
    private var standingResponse = StandingResponse(standing)

    private lateinit var standingPresenter: StandingPresenter

    @Mock
    private lateinit var view: StandingView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var repository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        standingPresenter = StandingPresenter(view, repository, gson, TestContextProvider())
    }

    @Test
    fun getStanding() {
        runBlocking {
            Mockito.`when`(repository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson(
                    "",
                    StandingResponse::class.java
                )
            ).thenReturn(standingResponse)

            standingPresenter.getStanding(id)

            Mockito.verify(view).showStanding(standing)
        }
    }
}