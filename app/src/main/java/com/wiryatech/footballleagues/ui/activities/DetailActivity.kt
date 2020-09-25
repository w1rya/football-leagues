package com.wiryatech.footballleagues.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.google.gson.Gson
import com.wiryatech.footballleagues.R
import com.wiryatech.footballleagues.adapters.SectionsPagerAdapter
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.detail.LeagueDetailPresenter
import com.wiryatech.footballleagues.detail.LeagueDetailView
import com.wiryatech.footballleagues.models.League
import com.wiryatech.footballleagues.utils.invisible
import com.wiryatech.footballleagues.utils.visible
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), LeagueDetailView {

    private lateinit var presenter: LeagueDetailPresenter

    companion object {
        const val EXTRA_LEAGUE = "extra_league"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initUI()

        val request = ApiRepository()
        val gson = Gson()
        presenter = LeagueDetailPresenter(this, request, gson)

        intent.getParcelableExtra<League>(EXTRA_LEAGUE)?.let { result ->
            result.idLeague?.let {
                presenter.getLeagueDetail(it.toString())
                initPager(it.toString())
            }
            result.strLeague?.let { tv_name.text = it }
            result.badge?.let { badge ->
                iv_badge.load(badge) {
                    placeholder(R.drawable.ic_round_sports_soccer_24_white)
                    size(120)
                }
            }
        }
    }

    private fun initUI() {
        btn_back.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun initPager(id: String) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.setData(id)
        vp_detail.adapter = sectionsPagerAdapter
        tab_detail.setupWithViewPager(vp_detail)
    }


    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showLeagueDetail(data: List<League>) {
        tv_gender.text = data[0].strGender
        tv_country.text = data[0].strCountry
        tv_year.text = data[0].intFormedYear.toString()
    }

}