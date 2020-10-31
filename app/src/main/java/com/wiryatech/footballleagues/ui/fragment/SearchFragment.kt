package com.wiryatech.footballleagues.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.wiryatech.footballleagues.R
import com.wiryatech.footballleagues.adapters.MatchAdapter
import com.wiryatech.footballleagues.adapters.TeamsAdapter
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.models.Match
import com.wiryatech.footballleagues.models.Team
import com.wiryatech.footballleagues.search.SearchPresenter
import com.wiryatech.footballleagues.search.SearchesView
import com.wiryatech.footballleagues.ui.activities.MatchActivity
import com.wiryatech.footballleagues.ui.activities.TeamActivity
import com.wiryatech.footballleagues.utils.Constants
import com.wiryatech.footballleagues.utils.invisible
import com.wiryatech.footballleagues.utils.visible
import kotlinx.android.synthetic.main.fragment_search.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.toast

class SearchFragment : Fragment(), SearchesView {

    private var matches: MutableList<Match> = mutableListOf()
    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: SearchPresenter
    private lateinit var matchAdapter: MatchAdapter
    private lateinit var teamsAdapter: TeamsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        matchAdapter = MatchAdapter(matches) {
            context?.toast(it.idEvent)
            startActivity<MatchActivity>(MatchActivity.EXTRA_EVENT to it.idEvent)
        }

        teamsAdapter = TeamsAdapter(teams) {
            startActivity<TeamActivity>(TeamActivity.EXTRA_TEAM to it.idTeam)
        }

        val request = ApiRepository()
        val gson = Gson()
        presenter = SearchPresenter(this, request, gson)

        initUI()
    }

    private fun initUI() {
        search.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    presenter.search(query)
                    presenter.search(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }

        rv_match.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = matchAdapter
        }

        rv_teams.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = teamsAdapter
        }
    }

    override fun showLoading() {
        progressBarSearch.visible()
    }

    override fun hideLoading() {
        progressBarSearch.invisible()
    }

    override fun showMatch(data: List<Match>) {
        iv_error.invisible()
        tv_error.invisible()

        tv_match.visible()
        rv_match.visible()

        matches.clear()
        matches.addAll(data)
        matchAdapter.notifyDataSetChanged()
    }

    override fun showTeams(data: List<Team>) {
        tv_team.visible()
        rv_teams.visible()

        teams.clear()
        teams.addAll(data)
        teamsAdapter.notifyDataSetChanged()
    }

    override fun showError(code: Int) {
        tv_team.invisible()
        tv_match.invisible()
        hideList()

        when (code) {
            Constants.SEARCH_NULL -> showNoData()
            Constants.NO_CONNECTION -> showNoConnection()
        }
    }

    private fun showNoData() {
        iv_error.setImageResource(R.drawable.ic_no_data)
        tv_error.text = getString(R.string.no_data)
        iv_error.visible()
        tv_error.visible()
    }

    private fun showNoConnection() {
        iv_error.setImageResource(R.drawable.ic_no_signal)
        tv_error.text = getString(R.string.no_connection)
        iv_error.visible()
        tv_error.visible()
    }

    private fun hideList() {
        rv_match.invisible()
        rv_teams.invisible()
    }

}