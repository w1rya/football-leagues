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
import com.wiryatech.footballleagues.adapters.MatchAdapter
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.matches.MatchListPresenter
import com.wiryatech.footballleagues.matches.MatchListView
import com.wiryatech.footballleagues.models.Match
import com.wiryatech.footballleagues.ui.activities.MatchActivity
import com.wiryatech.footballleagues.utils.Constants
import com.wiryatech.footballleagues.utils.invisible
import com.wiryatech.footballleagues.utils.visible
import kotlinx.android.synthetic.main.fragment_match.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.toast

class MatchFragment : Fragment(), MatchListView {

    companion object {
        const val ID = "id"
    }

    private var prevMatches: MutableList<Match> = mutableListOf()
    private var nextMatches: MutableList<Match> = mutableListOf()
    private lateinit var presenter: MatchListPresenter
    private lateinit var prevMatchAdapter: MatchAdapter
    private lateinit var nextMatchAdapter: MatchAdapter
    private lateinit var idLeague: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prevMatchAdapter = MatchAdapter(prevMatches) {
            context?.toast(it.idEvent)
            startActivity<MatchActivity>(MatchActivity.EXTRA_EVENT to it.idEvent)
        }

        nextMatchAdapter = MatchAdapter(nextMatches) {
            context?.toast(it.idEvent)
            startActivity<MatchActivity>(MatchActivity.EXTRA_EVENT to it.idEvent)
        }

        val request = ApiRepository()
        val gson = Gson()
        presenter = MatchListPresenter(this, request, gson)

        initUI()

        Log.d("BundleFragment1", "$savedInstanceState, $arguments")
        if (arguments != null) {
            idLeague = arguments?.getString(ID).toString()
            Log.d("Prev", "$arguments, $idLeague")
            presenter.getPrevMatch(idLeague)
            presenter.getNextMatch(idLeague)
        }

        swipeRefresh.setOnRefreshListener {
            presenter.getPrevMatch(idLeague)
            presenter.getNextMatch(idLeague)
        }
    }

    private fun initUI() {
        rv_prev_match.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = prevMatchAdapter
        }

        rv_next_match.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = nextMatchAdapter
        }
    }

    override fun showLoading() {
        progressBarMatch.visible()
    }

    override fun hideLoading() {
        progressBarMatch.invisible()
        swipeRefresh.isRefreshing = false
    }

    override fun showPrevMatch(data: List<Match>) {
        iv_no_connection.invisible()
        tv_no_connection.invisible()
        swipeRefresh.isRefreshing = false

        prevMatches.clear()
        prevMatches.addAll(data)
        prevMatchAdapter.notifyDataSetChanged()
    }

    override fun showNextMatch(data: List<Match>) {
        nextMatches.clear()
        nextMatches.addAll(data)
        nextMatchAdapter.notifyDataSetChanged()
    }

    override fun showError(code: Int) {
        when (code) {
            Constants.PREV_MATCH_NULL -> showNoPrevMatch()
            Constants.NEXT_MATCH_NULL -> showNoNextMatch()
            Constants.NO_CONNECTION -> showNoConnection()
        }
    }

    private fun showNoPrevMatch() {
        rv_prev_match.invisible()
        iv_no_data_prev.visible()
        tv_no_data_prev.visible()
    }

    private fun showNoNextMatch() {
        rv_next_match.invisible()
        iv_no_data_next.visible()
        tv_no_data_next.visible()
    }

    private fun showNoConnection() {
        swipeRefresh.isRefreshing = false

        tv_next_match.invisible()
        tv_prev_match.invisible()

        iv_no_connection.visible()
        tv_no_connection.visible()
    }

}