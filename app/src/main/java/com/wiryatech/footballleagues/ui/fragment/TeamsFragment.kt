package com.wiryatech.footballleagues.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.wiryatech.footballleagues.R
import com.wiryatech.footballleagues.adapters.TeamsAdapter
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.models.Team
import com.wiryatech.footballleagues.teams.TeamsPresenter
import com.wiryatech.footballleagues.teams.TeamsView
import com.wiryatech.footballleagues.ui.activities.TeamActivity
import com.wiryatech.footballleagues.utils.*
import kotlinx.android.synthetic.main.fragment_teams.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.toast

class TeamsFragment : Fragment(), TeamsView {

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamsPresenter
    private lateinit var teamsAdapter: TeamsAdapter
    private lateinit var idLeague: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teams, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        teamsAdapter = TeamsAdapter(teams) {
            context?.toast(it.idTeam)
            startActivity<TeamActivity>(TeamActivity.EXTRA_TEAM to it.idTeam)
        }

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamsPresenter(this, request, gson)

        initUI()

        if (arguments != null) {
            idLeague = arguments?.getString(MatchFragment.ID).toString()
            Log.d("Standing", "$arguments, $idLeague")
            presenter.getTeamList(idLeague)
        }

        swipeRefresh.setOnRefreshListener {
            presenter.getTeamList(idLeague)
        }
    }

    private fun initUI() {
        rv_teams.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = teamsAdapter
        }
    }

    override fun showLoading() {
        progressBarTeams.visible()
    }

    override fun hideLoading() {
        progressBarTeams.invisible()
    }

    override fun showTeamList(data: List<Team>) {
        iv_error.invisible()
        tv_error.invisible()
        swipeRefresh.isRefreshing = false

        teams.clear()
        teams.addAll(data)
        teamsAdapter.notifyDataSetChanged()
    }

    override fun showNoConnection() {
        swipeRefresh.isRefreshing = false
        iv_error.setImageResource(R.drawable.ic_no_signal)
        tv_error.text = getString(R.string.no_connection)
        iv_error.visible()
        tv_error.visible()
    }

}