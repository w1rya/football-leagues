package com.wiryatech.footballleagues.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.transform.BlurTransformation
import coil.transform.RoundedCornersTransformation
import com.google.gson.Gson
import com.wiryatech.footballleagues.R
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.matches.MatchDetailPresenter
import com.wiryatech.footballleagues.matches.MatchDetailView
import com.wiryatech.footballleagues.models.DetailMatch
import com.wiryatech.footballleagues.utils.invisible
import com.wiryatech.footballleagues.utils.visible
import kotlinx.android.synthetic.main.activity_match.*
import org.jetbrains.anko.design.snackbar

class MatchActivity : AppCompatActivity(), MatchDetailView {

    private lateinit var idEvent: String
    private lateinit var presenter: MatchDetailPresenter
    private lateinit var match: DetailMatch

    private var isFavorite: Boolean = false

    companion object {
        const val EXTRA_EVENT = "extra_event"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)

        val request = ApiRepository()
        val gson = Gson()
        presenter = MatchDetailPresenter(this, request, gson)

        intent.getStringExtra(EXTRA_EVENT)?.let {
            idEvent = it
            presenter.getMatchDetail(idEvent)
            Log.d("Presenter", "Send $idEvent to Presenter")
        }

        setFavState()
        initUI()

        swipeRefresh.setOnRefreshListener {
            presenter.getMatchDetail(idEvent)
            Log.d("SwipeRefresh", idEvent)
        }
    }

    private fun initUI() {
        btn_back.setOnClickListener {
            super.onBackPressed()
        }

        setFavIcon()

        btn_fav.setOnClickListener {
            if (isFavorite) delFromFav() else addToFav()
            isFavorite = !isFavorite
            setFavIcon()
        }
    }

    private fun setFavState() {
        isFavorite = presenter.setFavState(this, idEvent)
    }

    private fun addToFav() {
        presenter.addToFav(this, match)
    }

    private fun delFromFav() {
        presenter.delFromFav(this, idEvent)
    }

    private fun setFavIcon() {
        if (isFavorite) {
            btn_fav.setImageResource(R.drawable.ic_round_star_24_fill)
        } else {
            btn_fav.setImageResource(R.drawable.ic_round_star_24_border)
        }
    }

    override fun showLoading() {
        progressBarMatch.visible()
    }

    override fun hideLoading() {
        progressBarMatch.invisible()
    }

    override fun showSnackBar(message: String) {
        swipeRefresh.snackbar(message).show()
    }

    override fun showMatchDetail(data: List<DetailMatch>) {
        swipeRefresh.isRefreshing = false

        data[0].let { result ->
            match = DetailMatch(
                result.idEvent,
                result.strThumb,
                result.strEvent ,
                result.strLeague,
                result.strSeason,
                result.strHomeTeam,
                result.strAwayTeam,
                result.intHomeScore,
                result.intAwayScore,
                result.strHomeFormation,
                result.strHomeGoalDetails,
                result.strHomeRedCards,
                result.strHomeYellowCards,
                result.strAwayFormation,
                result.strAwayGoalDetails,
                result.strAwayRedCards,
                result.strAwayYellowCards,
                result.dateEvent,
                result.strVenue
            )

            if (result.strThumb != null) {
                iv_thumb.load(result.strThumb) {
                    crossfade(true)
                    placeholder(R.color.colorPrimary)
                    transformations(RoundedCornersTransformation(24f), BlurTransformation(this@MatchActivity, 0.50f))
                    error(R.drawable.ic_placeholder)
                }
            } else {
                iv_thumb.load(R.drawable.ic_placeholder) {
                    crossfade(true)
                    transformations(RoundedCornersTransformation(24f), BlurTransformation(this@MatchActivity, 0.50f))
                }
            }

            tv_name.text = result.strLeague
            tv_event.text = result.strEvent
            tv_season.text = result.strSeason
            tv_date.text = result.dateEvent
            result.strVenue?.let { tv_venue.text = it }

            tv_home_name.text = result.strHomeTeam
            result.intHomeScore?.let { tv_home_score.text = it.toString() }
            result.strHomeGoalDetails?.let { tv_home_goal.text = it }
            result.strHomeRedCards?.let { tv_home_red_card.text = it }
            result.strHomeYellowCards?.let { tv_home_yellow_card.text = it }
            result.strHomeFormation?.let { tv_home_formation.text = it }

            tv_away_name.text = result.strAwayTeam
            result.intAwayScore?.let { tv_away_score.text = it.toString() }
            result.strAwayGoalDetails?.let { tv_away_goal.text = it }
            result.strAwayRedCards?.let { tv_away_red_card.text = it }
            result.strAwayYellowCards?.let { tv_away_yellow_card.text = it }
            result.strAwayFormation?.let { tv_away_formation.text = it }
        }
    }

}