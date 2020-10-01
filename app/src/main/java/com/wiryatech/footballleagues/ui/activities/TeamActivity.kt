package com.wiryatech.footballleagues.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.gson.Gson
import com.wiryatech.footballleagues.R
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.models.Team
import com.wiryatech.footballleagues.teams.TeamsPresenter
import com.wiryatech.footballleagues.teams.TeamsView
import com.wiryatech.footballleagues.utils.invisible
import com.wiryatech.footballleagues.utils.visible
import kotlinx.android.synthetic.main.activity_team.*
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.design.snackbar

class TeamActivity : AppCompatActivity(), TeamsView {

    private lateinit var idTeam: String
    private lateinit var presenter: TeamsPresenter
    private lateinit var team: Team

    private var isFavorite: Boolean = false

    companion object {
        const val EXTRA_TEAM = "extra_team"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamsPresenter(this, request, gson)

        intent.getStringExtra(EXTRA_TEAM)?.let {
            idTeam = it
            presenter.getDetail(idTeam)
        }

        setFavState()
        initUI()

        swipeRefresh.setOnRefreshListener {
            presenter.getDetail(idTeam)
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
        isFavorite = presenter.setFavState(this, idTeam)
    }

    private fun addToFav() {
        presenter.addToFav(this, team)
    }

    private fun delFromFav() {
        presenter.delFromFav(this, idTeam)
    }

    private fun setFavIcon() {
        if (isFavorite) {
            btn_fav.setImageResource(R.drawable.ic_round_star_24_fill)
        } else {
            btn_fav.setImageResource(R.drawable.ic_round_star_24_border)
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<Team>) {
        swipeRefresh.isRefreshing = false

        data[0].let { result ->
            team = Team(
                result.idTeam,
                result.strTeam,
                result.strDescriptionEN,
                result.strTeamBadge,
                result.intFormedYear,
                result.strSport,
                result.strLeague,
                result.strGender,
                result.strCountry,
                result.strStadium,
                result.strTeamJersey,
                result.strTeamBanner,
                result.strStadiumThumb,
                result.strStadiumLocation,
                result.intStadiumCapacity
            )

            result.strTeamBadge.let { iv_team.load(it) }
            tv_name.text = result.strTeam
            tv_sport.text = result.strSport
            tv_gender.text = result.strGender
            tv_league.text = result.strLeague
            tv_country.text = result.strCountry
            tv_year.text = result.intFormedYear.toString()
            tv_desc.text = result.strDescriptionEN
            result.strStadiumThumb?.let {
                iv_stadium.load(it) {
                    crossfade(true)
                    transformations(RoundedCornersTransformation(16f))
                }
            }
            result.strStadiumLocation?.let { tv_location.text = it }
            result.intStadiumCapacity?.let { tv_capacity.text = it.toString()}
            result.strTeamBanner?.let {
                iv_banner.load(it) {
                    crossfade(true)
                    transformations(RoundedCornersTransformation(16f))
                    size(1000, 185)
                }
            }
            result.strTeamJersey?.let {
                iv_jersey.load(it) {
                    crossfade(true)
                }
            }
        }
    }

    override fun showSnackBar(message: String) {
        swipeRefresh.snackbar(message).show()
    }

    override fun showNoConnection() {
        swipeRefresh.isRefreshing = false
        swipeRefresh.longSnackbar(R.string.no_connection).show()
    }
}