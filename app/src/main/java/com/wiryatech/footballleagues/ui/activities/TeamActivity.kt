package com.wiryatech.footballleagues.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.google.gson.Gson
import com.wiryatech.footballleagues.R
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.models.Team
import com.wiryatech.footballleagues.teams.TeamsPresenter
import com.wiryatech.footballleagues.teams.TeamsView
import com.wiryatech.footballleagues.utils.*
import kotlinx.android.synthetic.main.activity_team.*

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
            Log.d("Presenter", "Send $idTeam to Presenter")
        }

        initUI()
    }

    private fun initUI() {
        btn_back.setOnClickListener {
            super.onBackPressed()
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<Team>) {
        data[0].let { result ->
            result.strTeamBadge.let { iv_team.load(it) }
            tv_name.text = result.strTeam
            tv_sport.text = result.strSport
            tv_gender.text = result.strGender
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

    override fun showNoConnection() {
        // Nothing to do here yet
    }
}